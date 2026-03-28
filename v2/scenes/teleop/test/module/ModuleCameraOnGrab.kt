package org.firstinspires.ftc.teamcode.v2.scenes.teleop.test.module

import com.acmerobotics.dashboard.FtcDashboard
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.v2.components.util.GrabColorPipeline
import org.firstinspires.ftc.teamcode.v2.modules.CameraOnGrab
import org.firstinspires.ftc.teamcode.v2.modules.superclasses.RobotPack
import org.firstinspires.ftc.teamcode.v2.scenes.superclasses.TeleOpPacker

@TeleOp(name = "MODULE - CamOnGrb", group = "module")
class ModuleCameraOnGrab : TeleOpPacker() {
    override fun init_(P: RobotPack) {
        val dash = FtcDashboard.getInstance().telemetry

        val camOnGrb = CameraOnGrab(P)
        val pipeline = GrabColorPipeline()

        camOnGrb.openWithPipeline(pipeline, true)

        linkerApi.bindVoid(
            { gamepad1.a },
            { true },
            { pipeline.triggerCapture = true }
        )

        linkerApi.bindVoid({ true }, { true }, {
            dash.addData("isOpen", camOnGrb.isOpened)
            dash.addData("detected", pipeline.detectedColor)
            dash.addData("area", pipeline.detectedArea)
            dash.addData("queue", pipeline.colorQueue.toList())
            dash.update()
        })
    }
}