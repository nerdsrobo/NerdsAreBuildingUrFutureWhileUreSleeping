package org.firstinspires.ftc.teamcode.v2.scenes.teleop.test.module

import com.acmerobotics.dashboard.FtcDashboard
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.v2.modules.ShooterV2
import org.firstinspires.ftc.teamcode.v2.modules.superclasses.RobotPack
import org.firstinspires.ftc.teamcode.v2.scenes.superclasses.TeleOpPacker
import org.firstinspires.ftc.teamcode.v2.util.dashconfigs.ConfigShooterController
import org.firstinspires.ftc.teamcode.v2.util.dashconfigs.ConfigShooterV2

@TeleOp(name = "MODULE - ShooterV2", group = "module")
class ModuleShooterV2(): TeleOpPacker() {
    override fun init_(P: RobotPack) {
        val Pdash = RobotPack(P.LOP, P.hwmp, FtcDashboard.getInstance().telemetry, gamepad1, gamepad2, linkerApi, alliance);

        val sht = ShooterV2(Pdash);

        linkerApi.bindDouble({true}, {!gamepad1.y && !gamepad1.a}, {gamepad1.left_stick_y.toDouble()}, {pw -> sht.SHT.power = pw});
        linkerApi.bindVoid({true}, {gamepad1.y}, {sht.setShtPower(1.0)});
        linkerApi.bindVoid({true}, {gamepad1.a}, {sht.setShtPower(-.5)});

        //linkerApi.bindVoid({true}, {gamepad1.dpad_up}, {sht.setAnglePos(ConfigShooterController.servoUpPos)});
        //linkerApi.bindVoid({true}, {gamepad1.dpad_down}, {sht.setAnglePos(ConfigShooterController.servoDownPos)});
        linkerApi.bindVoid({gamepad1.dpad_left}, {gamepad1.dpad_up}, {sht.ShtL.position = ConfigShooterController.servoUpPosL});
        linkerApi.bindVoid({gamepad1.dpad_left}, {gamepad1.dpad_down}, {sht.ShtL.position = ConfigShooterController.servoDownPosL});
        linkerApi.bindVoid({gamepad1.dpad_right}, {gamepad1.dpad_up}, {sht.ShtR.position = ConfigShooterController.servoUpPosR});
        linkerApi.bindVoid({gamepad1.dpad_right}, {gamepad1.dpad_down}, {sht.ShtR.position = ConfigShooterController.servoDownPosR});
        linkerApi.bindVoid({gamepad1.right_bumper}, {gamepad1.dpad_up}, {sht.setAnglePos(ConfigShooterController.servoUpPosL, ConfigShooterController.servoUpPosR)});
        linkerApi.bindVoid({gamepad1.right_bumper}, {gamepad1.dpad_down}, {sht.setAnglePos(ConfigShooterController.servoDownPosL, ConfigShooterController.servoDownPosR)});
        linkerApi.bindVoid({gamepad1.left_bumper}, {gamepad1.dpad_up}, {sht.setAnglePos(ConfigShooterV2.servoLFullUp, ConfigShooterV2.servoRFullUp)})

        linkerApi.bindVoid({true}, {true}, {Pdash.telemetry.addData("ticks", sht.getShtTicks()); Pdash.telemetry.update();});
    }
}