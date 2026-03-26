package org.firstinspires.ftc.teamcode.v2.scenes.teleop.test.component

import com.acmerobotics.dashboard.FtcDashboard
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.v2.components.AprilTagRuler
import org.firstinspires.ftc.teamcode.v2.components.LookAtGoalCalcs
import org.firstinspires.ftc.teamcode.v2.components.ShooterV2Controller
import org.firstinspires.ftc.teamcode.v2.components.util.Alliance
import org.firstinspires.ftc.teamcode.v2.modules.CameraOnShooter
import org.firstinspires.ftc.teamcode.v2.modules.ShooterV2
import org.firstinspires.ftc.teamcode.v2.modules.Sorter
import org.firstinspires.ftc.teamcode.v2.modules.TolkalkaV2
import org.firstinspires.ftc.teamcode.v2.modules.Wheelbase
import org.firstinspires.ftc.teamcode.v2.modules.superclasses.RobotPack
import org.firstinspires.ftc.teamcode.v2.scenes.superclasses.TeleOpPacker
import org.firstinspires.ftc.teamcode.v2.util.dashconfigs.ConfigShooterController

@TeleOp(name = "COMPONENT - LookAtGoalCalcs - BLUE", group = "component")
class ComponentLookAtGoalCalcsBlue(): ComponentLookAtGoalCalcs(Alliance.BLUE) {}

@TeleOp(name = "COMPONENT - LookAtGoalCalcs - RED", group = "component")
class ComponentLookAtGoalCalcsRed(): ComponentLookAtGoalCalcs(Alliance.RED) {}

open class ComponentLookAtGoalCalcs(alliance: Alliance): TeleOpPacker(alliance) {
    override fun init_(P: RobotPack) {

        val dashTelemetry = FtcDashboard.getInstance().telemetry;

        val wb = Wheelbase(P);
        val camOnSht = CameraOnShooter(P);
        val sht = ShooterV2(P);
        val tlk = TolkalkaV2(P);
        val srt = Sorter(P);
        val shtControl = ShooterV2Controller(P);
        val aprilTagRuler = AprilTagRuler(P);
        val calcToGoal = LookAtGoalCalcs(P);
        linkerApi.alwaysActiveComponents += arrayOf(shtControl, calcToGoal);

        var isNear = true;

        linkerApi.bindFlag({true}, {gamepad1.a}, {shtControl.targetW = if ( isNear ) ConfigShooterController.nearMotorW else ConfigShooterController.farMotorW;
            shtControl.control = true; shtControl.stop = false});
        linkerApi.bindFlag({true}, {gamepad1.b}, {shtControl.stop = false;});

        linkerApi.bindFlag({true}, {gamepad1.dpad_left}, {isNear = true});
        linkerApi.bindFlag({true}, {gamepad1.dpad_right}, {isNear = false});

        linkerApi.bindFlag({true}, {gamepad1.y}, {linkerApi.activeComponents.add(aprilTagRuler); aprilTagRuler.open(true);});
        linkerApi.bindFlag({true}, {camOnSht.isOpened}, {dashTelemetry.addData("cam", "opened"); dashTelemetry.update();})
        linkerApi.bindFlag({true}, {!gamepad1.y}, {linkerApi.activeComponents.remove(aprilTagRuler); aprilTagRuler.close();
            val calcs = calcToGoal.calcs(aprilTagRuler.absX, aprilTagRuler.absY,
                if ( isNear ) ConfigShooterController.nearMotorW else ConfigShooterController.farMotorW, isNear);
            dashTelemetry.addData("absX", aprilTagRuler.absX);
            dashTelemetry.addData("absY", aprilTagRuler.absY);
            dashTelemetry.addData("shooterW", shtControl.vel);
            dashTelemetry.addData("isNear", isNear);
            dashTelemetry.addData("rotRobot", calcs.rotRobot);
            dashTelemetry.addData("angle", calcs.shooterAngle);
            dashTelemetry.update();
            shtControl.setAngle(calcs.shooterAngle);
            });

        linkerApi.bindFlag({true}, {gamepad1.dpad_up}, {tlk.setServPower(1.0)});
        linkerApi.bindFlag({true}, {gamepad1.dpad_down}, {tlk.setServPower(0.0)});

        linkerApi.bindArray({true}, {true}, { arrayOf(gamepad1.left_stick_x.toDouble(), gamepad1.left_stick_y.toDouble(), gamepad1.right_stick_x.toDouble()) },
            { pws -> wb.setAxisPower(pws[0], pws[1], pws[2])} );
        linkerApi.bindDouble({true}, {true}, {(gamepad1.right_trigger - gamepad1.left_trigger).toDouble()*.4}, {pw -> srt.setSrtPower(pw)})


    }
}