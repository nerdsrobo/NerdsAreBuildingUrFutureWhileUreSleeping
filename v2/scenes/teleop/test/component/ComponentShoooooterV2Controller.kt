package org.firstinspires.ftc.teamcode.v2.scenes.teleop.test.component

import com.acmerobotics.dashboard.FtcDashboard
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.v2.components.ShooterV2Controller
import org.firstinspires.ftc.teamcode.v2.modules.ShooterV2
import org.firstinspires.ftc.teamcode.v2.modules.superclasses.RobotPack
import org.firstinspires.ftc.teamcode.v2.scenes.superclasses.TeleOpPacker

@TeleOp(name = "COMPONENT - ShooterV2Controller", group = "component")
class ComponentShoooooterV2Controller(): TeleOpPacker() {
    override fun init_(P: RobotPack) {
        val dashTelemetry = FtcDashboard.getInstance().telemetry;

        val sht = ShooterV2(P);
        val shtControl = ShooterV2Controller(P);
        linkerApi.activeComponents.add(shtControl);

        linkerApi.bindFlag({true}, {gamepad1.y}, {shtControl.targetW = 6.0; shtControl.stop = false;});
        linkerApi.bindFlag({true}, {gamepad1.b}, {shtControl.targetW = 0.0; shtControl.stop = true});
        linkerApi.bindFlag({true}, {gamepad1.dpad_up}, {shtControl.setAngle(35.0)});
        linkerApi.bindFlag({true}, {gamepad1.dpad_down}, {shtControl.setAngle(28.0)});
        linkerApi.bindVoid({true}, {true}, {
            dashTelemetry.addData("curr vel REV/SEC", shtControl.vel);
            dashTelemetry.addData("target vel REV/SEC", shtControl.targetW);
            dashTelemetry.addData("er", shtControl.targetW - shtControl.vel);
            dashTelemetry.addData("is on revs", shtControl.isOnRevolutions());
            dashTelemetry.addData("U", shtControl.U);
            dashTelemetry.update();
        })
    }
}