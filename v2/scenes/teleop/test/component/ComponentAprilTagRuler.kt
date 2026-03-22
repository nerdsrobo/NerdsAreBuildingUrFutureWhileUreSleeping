package org.firstinspires.ftc.teamcode.v2.scenes.teleop.test.component

import com.acmerobotics.dashboard.FtcDashboard
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.v2.components.AprilTagRuler
import org.firstinspires.ftc.teamcode.v2.components.util.Alliance
import org.firstinspires.ftc.teamcode.v2.modules.CameraOnShooter
import org.firstinspires.ftc.teamcode.v2.modules.Wheelbase
import org.firstinspires.ftc.teamcode.v2.modules.superclasses.RobotPack
import org.firstinspires.ftc.teamcode.v2.scenes.superclasses.TeleOpPacker

@TeleOp(name = "COMPONENT - AprilTagRuler - BLUE", group = "module")
class ComponentAprilTagRulerBlue(): ComponentAprilTagRuler(Alliance.BLUE) {}

@TeleOp(name = "COMPONENT - AprilTagRuler - RED", group = "module")
class ComponentAprilTagRulerRed(): ComponentAprilTagRuler(Alliance.RED) {}

open class ComponentAprilTagRuler(alliance: Alliance): TeleOpPacker(alliance) {
    override fun init_(P: RobotPack) {
        val dashTelemetry = FtcDashboard.getInstance().telemetry;
        val pDash = RobotPack(this, hardwareMap, dashTelemetry, gamepad1, gamepad2, linkerApi, alliance);

        val camOnSht = CameraOnShooter(pDash);
        val wb = Wheelbase(pDash);
        val aprilTagRuler = AprilTagRuler(pDash);
        linkerApi.activeComponents.add(aprilTagRuler);
        aprilTagRuler.openTest();

        linkerApi.bindArray({true}, {true},
            { arrayOf(gamepad1.left_stick_x.toDouble(), -gamepad1.left_stick_y.toDouble(), gamepad1.right_stick_x.toDouble()) },
            {pws -> wb.setAxisPower(pws[0], pws[1], pws[2])});
        linkerApi.bindVoid({true}, {true}, {
            dashTelemetry.addData("motif", aprilTagRuler.motif);
            dashTelemetry.addData("absX", aprilTagRuler.absX);
            dashTelemetry.addData("absY", aprilTagRuler.absY);
            dashTelemetry.addData("angle", aprilTagRuler.lastAngle);
            dashTelemetry.addData("isUpdated", aprilTagRuler.absUpdated);
            dashTelemetry.addData("relX", aprilTagRuler.lastRelCoords[0]);
            dashTelemetry.addData("relY", aprilTagRuler.lastRelCoords[1]);
            dashTelemetry.addData("relAlpha", aprilTagRuler.lastRelCoords[2]);
            dashTelemetry.update();
        })
    }
}