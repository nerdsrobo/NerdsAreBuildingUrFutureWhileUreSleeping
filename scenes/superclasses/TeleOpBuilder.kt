package org.firstinspires.ftc.teamcode.scenes.superclasses

import GamepadAPI
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.modules.superclasses.RobotPack

abstract class TeleOpBuilder() : LinearOpMode() {
    val GAPI = GamepadAPI();
    val P = RobotPack(this, hardwareMap, telemetry, gamepad1, gamepad2);
    override fun runOpMode() {
        waitForStart();
        while ( opModeIsActive() ) {
            GAPI.tick();
        }
    }
}