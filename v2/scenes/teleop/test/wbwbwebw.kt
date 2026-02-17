package org.firstinspires.ftc.teamcode.v2.scenes.teleop.test

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import org.firstinspires.ftc.teamcode.modules.wheelbase.versions.WheelbaseMecanumV1
import org.firstinspires.ftc.teamcode.v2.modules.Wheelbase
import org.firstinspires.ftc.teamcode.v2.modules.superclasses.RobotPack
import org.firstinspires.ftc.teamcode.v2.scenes.superclasses.AutonomousPacker

@Autonomous(name="тырыпыры", group = "test")
class wbwbwebw() : AutonomousPacker() {
    var wb: Wheelbase? = null;
    override fun init_(P: RobotPack) {
        wb = Wheelbase(P);
    }

    override fun moves(P: RobotPack) {
        wb?.clockwiseTest();
        Thread.sleep(10000);
    }

}