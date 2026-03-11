package org.firstinspires.ftc.teamcode.v2.scenes.superclasses

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.GamepadAPI
import org.firstinspires.ftc.teamcode.v2.modules.superclasses.RobotPack
import org.firstinspires.ftc.teamcode.util.Utils
import org.firstinspires.ftc.teamcode.v2.LinkerAPI
import org.firstinspires.ftc.teamcode.v2.components.util.Alliance

abstract class TeleOpPacker(val alliance: Alliance = Alliance.BLUE) : LinearOpMode() {
    //val GAPI = GamepadAPI();
    val linkerApi = LinkerAPI();
    val utils = Utils();
    abstract fun init_(P: RobotPack);
    override fun runOpMode() {
        val P = RobotPack(this, hardwareMap, telemetry, gamepad1, gamepad2, linkerApi, alliance);
        init_(P);
        waitForStart();
        linkerApi.executeOnOpModeStartHandlers();
        while ( opModeIsActive() ) {
            //GAPI.tick();
            linkerApi.tick();
        }
    }
}