package org.firstinspires.ftc.teamcode.v2.scenes.teleop.test.module

import com.acmerobotics.dashboard.FtcDashboard
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.v2.components.util.TestCamPipeline
import org.firstinspires.ftc.teamcode.v2.modules.CameraOnShooter
import org.firstinspires.ftc.teamcode.v2.modules.superclasses.RobotPack
import org.firstinspires.ftc.teamcode.v2.scenes.superclasses.TeleOpPacker

@TeleOp(name = "MODULE - CamOnSht", group = "module")
class ModuleCameraOnShooter(): TeleOpPacker() {
    override fun init_(P: RobotPack) {
        val dashTelemetry = FtcDashboard.getInstance().telemetry;

        val camOnSht = CameraOnShooter(P);

        camOnSht.openWithPipeline(TestCamPipeline(), true);

        linkerApi.bindVoid({true}, {true}, {dashTelemetry.addData("isOpen", camOnSht.isOpened); dashTelemetry.update();})
    }
}