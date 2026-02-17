package org.firstinspires.ftc.teamcode.scenes.autonomous.test

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import org.firstinspires.ftc.teamcode.modules.superclasses.RobotPack
import org.firstinspires.ftc.teamcode.modules.wheelbase.versions.WheelbaseMecanumV1
import org.firstinspires.ftc.teamcode.scenes.superclasses.AutonomousBuilder

@Autonomous(name="LEGACY - лишь проезжая достаточно далеко мы получаем полный балл за съезд", group="legacy")
class AutoTimerLong() : AutonomousBuilder() {
    var wb: WheelbaseMecanumV1? = null;
    override fun init_(P: RobotPack) {
        wb = WheelbaseMecanumV1(P);
    }
    override fun moves(P: RobotPack) {
        wb?.setAxisPower(0.0, 1.0, 0.0);
        utils.delay(4000);
        wb?.setAxisPower(0.0, 0.0, 0.0);
        utils.delay(1000);
    }
}