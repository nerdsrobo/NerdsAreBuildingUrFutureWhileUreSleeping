package org.firstinspires.ftc.teamcode.scenes.autonomous.test

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import org.firstinspires.ftc.teamcode.modules.shooter.versions.ShooterVertV1
import org.firstinspires.ftc.teamcode.modules.sort.versions.SortSingleAndServosV1
import org.firstinspires.ftc.teamcode.modules.superclasses.RobotPack
import org.firstinspires.ftc.teamcode.modules.wheelbase.WheelbaseSuper4Wheel
import org.firstinspires.ftc.teamcode.modules.wheelbase.versions.WheelbaseMecanumV1
import org.firstinspires.ftc.teamcode.scenes.superclasses.AutonomousBuilder

@Autonomous(name = "праймовый дважды")
class Lolbl() : AutonomousBuilder() {
    var wb: WheelbaseMecanumV1? = null;
    var sht: ShooterVertV1? = null;
    var srt: SortSingleAndServosV1? = null;
    override fun init_(P: RobotPack) {
        wb = WheelbaseMecanumV1(P);
        sht = ShooterVertV1(P);
        srt = SortSingleAndServosV1(P);
    }

    override fun moves(P: RobotPack) {

        val TLK_L_UP = 1.0;
        val TLK_L_DWN = 0.3;
        val TLK_R_UP = 0.0;
        val TLK_R_DWN = .7;
        val TLK_L_STP = .004;
        val TLK_R_STP = -.004;
        var isUp = false;
        var TLK_L = TLK_L_DWN;
        var TLK_R = TLK_R_DWN;


        wb?.setAxisPower(0.0, -.6, 0.0);
        Thread.sleep(800);
        wb?.setAxisPower(0.0, 0.0, -.6);
        Thread.sleep(650);
        wb?.setAxisPower(0.0, -.6, 0.0);
        Thread.sleep(1200);
        wb?.setAxisPower(0.0, 0.0, 0.0);
        sht?.setMtPowerShooter(0.85);
        Thread.sleep(4500);
        //srt?.servUp();
        while ( isUp ) {
            TLK_L += TLK_L_STP;
            TLK_R += TLK_R_STP;
            srt?.TLK_L?.position = TLK_L;
            srt?.TLK_R?.position = TLK_R;
            if ((Math.abs(TLK_L - TLK_L_UP) < .05) || (Math.abs(TLK_R - TLK_R_UP) < .05)) {
                srt?.servUp();
                isUp = false;
            }
        }
        Thread.sleep(1200);
        srt?.servDown();
        sht?.setMtPowerShooter(0.0);
        Thread.sleep(8000)
        wb?.setAxisPower(0.0, 0.0, .6);
        Thread.sleep(750);
        wb?.setAxisPower(0.0, -.6, 0.0);
        Thread.sleep(1800);
        wb?.setAxisPower(0.0, 0.0, 0.0);
        Thread.sleep(3000);
    }
}