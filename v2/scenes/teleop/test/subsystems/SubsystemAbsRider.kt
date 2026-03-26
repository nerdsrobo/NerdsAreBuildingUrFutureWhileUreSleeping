package org.firstinspires.ftc.teamcode.v2.scenes.teleop.test.subsystems

import com.acmerobotics.dashboard.FtcDashboard
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.v2.components.AngleSponsor
import org.firstinspires.ftc.teamcode.v2.components.AprilTagRuler
import org.firstinspires.ftc.teamcode.v2.components.LookAtGoalCalcs
import org.firstinspires.ftc.teamcode.v2.components.util.UsefulFuncs
import org.firstinspires.ftc.teamcode.v2.modules.CameraOnShooter
import org.firstinspires.ftc.teamcode.v2.modules.Imu
import org.firstinspires.ftc.teamcode.v2.modules.Wheelbase
import org.firstinspires.ftc.teamcode.v2.modules.superclasses.RobotPack
import org.firstinspires.ftc.teamcode.v2.scenes.superclasses.TeleOpPacker
import org.firstinspires.ftc.teamcode.v2.systems.HogRider
import org.firstinspires.ftc.teamcode.v2.util.dashconfigs.ConfigShooterController

@TeleOp(name = "SUBSYSTEM - Absolute Rider", group = "subsystem")
class SubsystemAbsRider() : TeleOpPacker() {
    override fun init_(P: RobotPack) {
        val dashTelemetry = FtcDashboard.getInstance().telemetry;

        val wb = Wheelbase(P);
        val imu = Imu(P);
        val camOnSht = CameraOnShooter(P);

        val aprilTagRuler = AprilTagRuler(P);
        val ass = AngleSponsor(P);
        val calcToGoal = LookAtGoalCalcs(P);

        linkerApi.alwaysActiveComponents.add(ass);

        val hogRider = HogRider(P);
        hogRider.updateByImu = true;
        hogRider.powerAxis = false;
        hogRider.maxNormPw = .4;

        linkerApi.bindArray({!camOnSht.isOpened}, {true}, { arrayOf(gamepad1.left_stick_x.toDouble(), gamepad1.left_stick_y.toDouble(), gamepad1.right_stick_x.toDouble()) },
            { pws -> wb.setAxisPower(pws[0], pws[1], pws[2])} );
        linkerApi.bindFlag({true}, {gamepad1.y}, {
            aprilTagRuler.open(true);
            linkerApi.activeComponents.add(aprilTagRuler);
        })
        linkerApi.bindFlag({true}, {camOnSht.isOpened}, {dashTelemetry.addData("cam", "opened"); dashTelemetry.update();});
        linkerApi.bindFlag({true}, {!gamepad1.y}, {
            val calcs = calcToGoal.calcs(
                360.0/2, 360.0/2,
                ConfigShooterController.nearMotorW, true
            );
            hogRider.controlXY = true; hogRider.controlAngle = true; hogRider.powerAxis = true;
            hogRider.targetX = 360.0/2; hogRider.targetY = 360.0/2;
            hogRider.targetAngle = calcs.rotRobot;
        });
        linkerApi.bindFlag({camOnSht.isOpened}, {gamepad1.b}, {
            hogRider.controlXY = false; hogRider.controlAngle = false; hogRider.powerAxis = false;
            linkerApi.activeComponents.remove(aprilTagRuler);
            aprilTagRuler.close();
        })
        linkerApi.bindVoid({true}, {camOnSht.isOpened}, {
            dashTelemetry.addData("absX", aprilTagRuler.absX);
            dashTelemetry.addData("absY", aprilTagRuler.absY);
            dashTelemetry.addData("pwX", hogRider.pwX);
            dashTelemetry.addData("pwY", hogRider.pwY);
            dashTelemetry.addData("pwR", hogRider.pwR);
            dashTelemetry.addData("Er X", hogRider.targetX - aprilTagRuler.absX);
            dashTelemetry.addData("Er Y", hogRider.targetY - aprilTagRuler.absY);
            dashTelemetry.addData("Er R", hogRider.targetAngle - ass.getCurrAngle());
            dashTelemetry.addData("Er R 180", - UsefulFuncs.constrTo180(hogRider.angle - hogRider.targetAngle));
            dashTelemetry.update();
        })
    }
}