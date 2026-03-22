package org.firstinspires.ftc.teamcode.v2.scenes.teleop.test.component

import com.acmerobotics.dashboard.FtcDashboard
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.v2.components.AngleSponsor
import org.firstinspires.ftc.teamcode.v2.modules.Imu
import org.firstinspires.ftc.teamcode.v2.modules.Wheelbase
import org.firstinspires.ftc.teamcode.v2.modules.superclasses.RobotPack
import org.firstinspires.ftc.teamcode.v2.scenes.superclasses.TeleOpPacker

@TeleOp(name = "COMPONENT - AngleSponsor", group = "component")
class ComponentAngleSponsor(): TeleOpPacker() {
    override fun init_(P: RobotPack) {
        val dashTelemetry = FtcDashboard.getInstance().telemetry;
        val pDash = RobotPack(this, hardwareMap, dashTelemetry, gamepad1, gamepad2, linkerApi, alliance);

        val wb = Wheelbase(pDash);
        val imu = Imu(pDash);
        val ass = AngleSponsor(pDash);
        linkerApi.activeComponents.add(ass);

        linkerApi.bindDouble({true}, {true}, {gamepad1.right_stick_x.toDouble()}, {pw -> wb.setAxisPower(.0, .0, pw)});
        linkerApi.bindVoid({true}, {gamepad1.a}, {
            ass.angleOffset = (-imu.getAngle()).toDouble();
        })
        linkerApi.bindVoid({true}, {true}, {
            dashTelemetry.addData("imu real", imu.getAngle());
            dashTelemetry.addData("curr ass", ass.getCurrAngle());
            dashTelemetry.addData("angle offset", ass.angleOffset)
            dashTelemetry.update();
        })
    }
}