package org.firstinspires.ftc.teamcode.scenes.superclasses

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.modules.superclasses.RobotPack
import org.firstinspires.ftc.teamcode.util.Utils

abstract class AutonomousBuilder(): LinearOpMode() {
    val utils = Utils();
    abstract fun init_(P: RobotPack);
    abstract fun moves(P: RobotPack);
    override fun runOpMode() {
        val P = RobotPack(this, hardwareMap, telemetry, gamepad1, gamepad2);
        init_(P);
        waitForStart();
        moves(P);
    }

}