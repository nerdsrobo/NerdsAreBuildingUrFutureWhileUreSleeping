package org.firstinspires.ftc.teamcode.scenes.autonomous.test

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import org.firstinspires.ftc.teamcode.modules.shooter.versions.ShooterVertV1
import org.firstinspires.ftc.teamcode.modules.sort.versions.SortSingleAndServosV1
import org.firstinspires.ftc.teamcode.modules.superclasses.RobotPack
import org.firstinspires.ftc.teamcode.modules.wheelbase.WheelbaseSuper4Wheel
import org.firstinspires.ftc.teamcode.modules.wheelbase.versions.WheelbaseMecanumV1
import org.firstinspires.ftc.teamcode.scenes.superclasses.AutonomousBuilder

@Autonomous(name = "праймовый")
class Lol() : AutonomousBuilder() {
    var wb: WheelbaseMecanumV1? = null;
    var sht: ShooterVertV1? = null;
    var srt: SortSingleAndServosV1? = null;
    override fun init_(P: RobotPack) {
        wb = WheelbaseMecanumV1(P);
        sht = ShooterVertV1(P);
        srt = SortSingleAndServosV1(P);
    }

    override fun moves(P: RobotPack) {
        wb?.setAxisPower(0.0, -.6, 0.0);
        Thread.sleep(800);
        wb?.setAxisPower(0.0, 0.0, .6);
        Thread.sleep(400);
        wb?.setAxisPower(0.0, -.6, 0.0);
        Thread.sleep(800);
        wb?.setAxisPower(0.0, 0.0, 0.0);
        sht?.setMtPowerShooter(1.0);
        Thread.sleep(1000);
        srt?.servUp();
        Thread.sleep(1000);
        srt?.servDown();
        sht?.setMtPowerShooter(0.0);
        wb?.setAxisPower(0.0, 0.0, -.6);
        Thread.sleep(400);
        wb?.setAxisPower(0.0, -.6, 0.0);
        Thread.sleep(700);
        wb?.setAxisPower(0.0, 0.0, 0.0);
        Thread.sleep(3000);
    }
}