package org.firstinspires.ftc.teamcode.v2.scenes.superclasses

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.GamepadAPI
import org.firstinspires.ftc.teamcode.v2.modules.superclasses.RobotPack
import org.firstinspires.ftc.teamcode.util.Utils
import org.firstinspires.ftc.teamcode.v2.LinkerAPI

abstract class TeleOpPacker() : LinearOpMode() {
    //val GAPI = GamepadAPI();
    val linkerApi = LinkerAPI();
    val utils = Utils();
    abstract fun init_(P: RobotPack);
    override fun runOpMode() {
        val P = RobotPack(this, hardwareMap, telemetry, gamepad1, gamepad2, linkerApi);
        init_(P);
        waitForStart();
        while ( opModeIsActive() ) {
            //GAPI.tick();
            linkerApi.tick();
        }
    }
}