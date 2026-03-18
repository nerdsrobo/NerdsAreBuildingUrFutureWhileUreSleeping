package org.firstinspires.ftc.teamcode.v2.scenes.teleop.test

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.CRServo
import com.qualcomm.robotcore.hardware.Servo
import org.firstinspires.ftc.teamcode.v2.modules.superclasses.RobotPack
import org.firstinspires.ftc.teamcode.v2.scenes.superclasses.TeleOpPacker

@TeleOp(name = "матюха", group = "test")
class Servochok() : LinearOpMode() {
    override fun runOpMode() {
        val s = hardwareMap.get(CRServo::class.java, "ss");
        s.power = 1.0;
        val ss = hardwareMap.get(Servo::class.java, "ss3");
        ss.position = .2;
        waitForStart();
        s.power = -1.0;
        ss.position = .8;
        while ( opModeIsActive() ) {}
    }
}