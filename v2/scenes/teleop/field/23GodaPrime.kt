package org.firstinspires.ftc.teamcode.v2.scenes.teleop.field

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.v2.components.AngleSponsor
import org.firstinspires.ftc.teamcode.v2.components.AprilTagRuler
import org.firstinspires.ftc.teamcode.v2.components.GrabController
import org.firstinspires.ftc.teamcode.v2.components.LookAtGoalCalcs
import org.firstinspires.ftc.teamcode.v2.components.ShooterV2Controller
import org.firstinspires.ftc.teamcode.v2.components.SorterControllerV2
import org.firstinspires.ftc.teamcode.v2.components.util.Alliance
import org.firstinspires.ftc.teamcode.v2.components.util.ArtefactColor
import org.firstinspires.ftc.teamcode.v2.modules.CameraOnGrab
import org.firstinspires.ftc.teamcode.v2.modules.CameraOnShooter
import org.firstinspires.ftc.teamcode.v2.modules.Grab
import org.firstinspires.ftc.teamcode.v2.modules.Imu
import org.firstinspires.ftc.teamcode.v2.modules.ShooterV2
import org.firstinspires.ftc.teamcode.v2.modules.Sorter
import org.firstinspires.ftc.teamcode.v2.modules.TolkalkaV2
import org.firstinspires.ftc.teamcode.v2.modules.Wheelbase
import org.firstinspires.ftc.teamcode.v2.modules.superclasses.RobotPack
import org.firstinspires.ftc.teamcode.v2.scenes.superclasses.TeleOpPacker
import org.firstinspires.ftc.teamcode.v2.systems.HogRider
import org.firstinspires.ftc.teamcode.v2.systems.ShootingSystem
import kotlin.math.abs

@TeleOp(name = "Бородавочный 2-3 года прайм - синий")
class T23GodaPrimeBlue : T23GodaPrime(Alliance.BLUE)

@TeleOp(name = "Бородавочный 2-3 года прайм - красный")
class T23GodaPrimeRed : T23GodaPrime(Alliance.RED)

open class T23GodaPrime(alliance: Alliance) : TeleOpPacker(alliance) {
    override fun init_(P: RobotPack) {
        val grb = Grab(P)
        val sht = ShooterV2(P)
        val tlk = TolkalkaV2(P)
        val srt = Sorter(P)
        val wb = Wheelbase(P)
        val imu = Imu(P)
        val camGrb = CameraOnGrab(P)
        val camSht = CameraOnShooter(P)

        val ass = AngleSponsor(P)
        linkerApi.alwaysActiveComponents.add(ass)

        val aprilTagRuler = AprilTagRuler(P)
        val grbControl = GrabController(P)
        LookAtGoalCalcs(P)
        val shtControl = ShooterV2Controller(P)
        val srtControl = SorterControllerV2(P)
        linkerApi.alwaysActiveComponents.add(srtControl)

        val hogRider = HogRider(P)
        val shtSys = ShootingSystem(P)

        hogRider.updateByImu = true
        hogRider.controlAngle = false

        shtSys.initSort()
        shtSys.setTargetAngleHandler = { angle -> hogRider.setRotation(angle) }

        linkerApi.bindVoid({ true }, { true }, {
            P.telemetry.addData("pwX", hogRider.pwX)
            P.telemetry.addData("pwY", hogRider.pwY)
            P.telemetry.addData("pwR", hogRider.pwR)
            P.telemetry.addData("pwROverride", hogRider.pwROverride)
            P.telemetry.addData("angle", hogRider.angle)
            P.telemetry.addData("targetAngle", hogRider.targetAngle)
            P.telemetry.addData("controlAngle", hogRider.controlAngle)
            P.telemetry.addData("LF", wb.LF.power)
            P.telemetry.addData("RF", wb.RF.power)
            P.telemetry.addData("LB", wb.LB.power)
            P.telemetry.addData("RB", wb.RB.power)
            P.telemetry.update()
        })

        linkerApi.bindArray(
            { !shtSys.blockMoving && linkerApi.state != "waitforshoot" }, { true },
            { arrayOf(gamepad1.left_stick_x.toDouble(), gamepad1.left_stick_y.toDouble()) },
            { pws -> hogRider.pwX = pws[0]; hogRider.pwY = pws[1]; hogRider.controlXY = false }
        )
        var rotateFlag = false
        linkerApi.bindDouble(
            { !shtSys.blockMoving && linkerApi.state != "waitforshoot" }, { true },
            { gamepad1.right_stick_x.toDouble() },
            { pw ->
                if (abs(pw) > .08) {
                    if (!rotateFlag) rotateFlag = true
                    hogRider.pwROverride = pw
                } else {
                    if (rotateFlag) {
                        rotateFlag = false
                        hogRider.targetAngle = hogRider.angle
                        hogRider.controlAngle = true
                    }
                }
            }
        )

        linkerApi.bindFlag({ shtSys.state == "default" }, { gamepad1.right_bumper }, {
            grbControl.readColor = true
            shtSys.grbMove = 1
        })
        linkerApi.bindFlag({ shtSys.state == "default" }, { gamepad1.left_bumper }, {
            grbControl.readColor = false
            shtSys.grbMove = 0
        })

        linkerApi.bindFlag({ shtSys.state == "default" }, { gamepad1.x }, {
            shtSys.goSort(true)
            linkerApi.state = "readytoorder"
        })
        linkerApi.bindFlag({ shtSys.state == "default" }, { gamepad1.b }, {
            shtSys.goSort(false)
            linkerApi.state = "readytoorder"
        })
        linkerApi.bindFlag({ linkerApi.state == "readytoexec" }, { gamepad1.y }, {
            shtSys.execute(hogRider.absX, hogRider.absY)
            linkerApi.state = "waitforshoot"
        })

        linkerApi.bindFlag({ shtSys.state == "default" }, { gamepad2.a }, {
            shtSys.grabbedArtefact()
        })
        linkerApi.bindFlag({ linkerApi.state == "readytoorder" }, { gamepad2.dpad_up }, {
            shtSys.addToOrder(ArtefactColor.PURPLE)
        })
        linkerApi.bindFlag({ linkerApi.state == "readytoorder" }, { gamepad2.dpad_down }, {
            shtSys.addToOrder(ArtefactColor.GREEN)
        })
        linkerApi.bindFlag({ linkerApi.state == "readytoorder" }, { gamepad2.b }, {
            shtSys.clearOrder()
        })
        linkerApi.bindFlag({ linkerApi.state == "readytoorder" }, { gamepad2.x }, {
            shtSys.placeNoOrder()
            linkerApi.state = "readytoexec"
        })

        linkerApi.bindFlag({ linkerApi.state == "readytoorder" }, { gamepad2.y }, {
            shtSys.placeOrder()
            linkerApi.state = "readytoexec"
        })

        linkerApi.bindVoid({ linkerApi.state == "waitforshoot" }, { shtSys.state == "default" }, {
            linkerApi.state = "default"
        })

        linkerApi.onState("default") {
            linkerApi.activeComponents.remove(shtControl)
            linkerApi.activeComponents.add(grbControl)
            grbControl.open(false)
        }
        linkerApi.onState("readytoorder") {
            linkerApi.activeComponents.remove(grbControl)
            grbControl.close()
            linkerApi.activeComponents.add(shtControl)
            linkerApi.activeComponents.add(aprilTagRuler)
            aprilTagRuler.open(false)
        }
        linkerApi.onState("waitforshoot") {
            hogRider.pwX = .0; hogRider.pwY = .0; hogRider.pwROverride = .0
            linkerApi.activeComponents.remove(aprilTagRuler)
            aprilTagRuler.close()
        }
    }
}
