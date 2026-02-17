package org.firstinspires.ftc.teamcode.v2.scenes.superclasses

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.v2.modules.superclasses.RobotPack
import org.firstinspires.ftc.teamcode.util.Utils
import org.firstinspires.ftc.teamcode.v2.LinkerAPI

abstract class AutonomousPacker(): LinearOpMode() {
    val utils = Utils();
    val linkerApi = LinkerAPI();
    abstract fun init_(P: RobotPack);
    abstract fun moves(P: RobotPack);
    override fun runOpMode() {
        val P = RobotPack(this, hardwareMap, telemetry, gamepad1, gamepad2, linkerApi);
        init_(P);
        waitForStart();
        moves(P);
    }

}