package org.firstinspires.ftc.teamcode.v2.scenes.teleop.test.module

import com.acmerobotics.dashboard.FtcDashboard
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.v2.modules.Imu
import org.firstinspires.ftc.teamcode.v2.modules.Wheelbase
import org.firstinspires.ftc.teamcode.v2.modules.superclasses.RobotPack
import org.firstinspires.ftc.teamcode.v2.scenes.superclasses.TeleOpPacker

@TeleOp(name = "MODULE - Imu", group = "module")
class ModuleImu(): TeleOpPacker() {
    override fun init_(P: RobotPack) {
        val dashTelemetry = FtcDashboard.getInstance().telemetry;
        val Pdash = RobotPack(P.LOP, P.hwmp, dashTelemetry, gamepad1, gamepad2, linkerApi, alliance);

        val wb = Wheelbase(Pdash);
        val imu = Imu(Pdash);

        linkerApi.bindVoid({true}, {true}, {dashTelemetry.addData("angle", imu.getAngle()); dashTelemetry.update();});
        linkerApi.bindDouble({true}, {true}, {gamepad1.right_stick_x.toDouble()*.7}, {pw -> wb.setAxisPower(.0, .0, pw)});
    }
}