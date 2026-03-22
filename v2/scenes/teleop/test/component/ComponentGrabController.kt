package org.firstinspires.ftc.teamcode.v2.scenes.teleop.test.component

import com.acmerobotics.dashboard.FtcDashboard
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.v2.components.GrabController
import org.firstinspires.ftc.teamcode.v2.modules.CameraOnGrab
import org.firstinspires.ftc.teamcode.v2.modules.Grab
import org.firstinspires.ftc.teamcode.v2.modules.superclasses.RobotPack
import org.firstinspires.ftc.teamcode.v2.scenes.superclasses.TeleOpPacker

@TeleOp(name = "COMPONENT - GrabController", group = "component")
class ComponentGrabController(): TeleOpPacker() {
    override fun init_(P: RobotPack) {
        val dashTelemetry = FtcDashboard.getInstance().telemetry;
        val pDash = RobotPack(this, hardwareMap, dashTelemetry, gamepad1, gamepad2, linkerApi, alliance);

        val camOnGrb = CameraOnGrab(pDash);
        val grb = Grab(pDash);
        val grbControl = GrabController(pDash);
        linkerApi.activeComponents.add(grbControl);
        grbControl.openTest();

        linkerApi.bindVoid({true}, {gamepad1.dpad_up}, {grbControl.take()});
        linkerApi.bindVoid({true}, {gamepad1.dpad_right}, {grbControl.stop()});
        linkerApi.bindVoid({true}, {gamepad1.dpad_down}, {grbControl.spit()});
        linkerApi.bindVoid({true}, {gamepad1.a}, {grbControl.swallow()});

        linkerApi.bindVoid({true}, {true}, {
            for ( art in grbControl.artefacts ) {
                dashTelemetry.addData("art x: " + art.x + " y: " + art.y + " col", art.color);
            }
            dashTelemetry.addData("snapped", if ( grbControl.snapped ) grbControl.lastSnappedColor else "неа");
            dashTelemetry.update();
        })

    }
}