package org.firstinspires.ftc.teamcode.v2.scenes.superclasses

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.v2.modules.superclasses.RobotPack
import org.firstinspires.ftc.teamcode.util.Utils
import org.firstinspires.ftc.teamcode.v2.LinkerAPI
import org.firstinspires.ftc.teamcode.v2.components.util.Alliance

abstract class AutonomousPacker(val alliance: Alliance = Alliance.BLUE): LinearOpMode() {
    val utils = Utils();
    val linkerApi = LinkerAPI();
    abstract fun init_(P: RobotPack);
    abstract fun moves(P: RobotPack);
    override fun runOpMode() {
        val P = RobotPack(this, hardwareMap, telemetry, gamepad1, gamepad2, linkerApi, alliance);
        init_(P);
        waitForStart();
        linkerApi.executeOnOpModeStartHandlers();
        moves(P);
    }

}