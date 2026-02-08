package org.firstinspires.ftc.teamcode.scenes.teleop.field

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.modules.grab.versions.GrabSingleV1
import org.firstinspires.ftc.teamcode.modules.shooter.versions.ShooterVertV1
import org.firstinspires.ftc.teamcode.modules.sort.versions.SortSingleAndServosV1
import org.firstinspires.ftc.teamcode.modules.superclasses.RobotPack
import org.firstinspires.ftc.teamcode.modules.wheelbase.versions.WheelbaseMecanumV1
import org.firstinspires.ftc.teamcode.scenes.superclasses.TeleOpBuilder

@TeleOp(name="2-3 года")
public class Jan10() : TeleOpBuilder() {
    override fun init_(P: RobotPack) {
        val wb = WheelbaseMecanumV1(P);
        val sht = ShooterVertV1(P);
        val srt = SortSingleAndServosV1(P);
        val grab = GrabSingleV1(P);
        var hldr = false;
        val TLK_L_UP = 1.0;
        val TLK_L_DWN = 0.3;
        val TLK_R_UP = 0.0;
        val TLK_R_DWN = .7;
        val TLK_L_STP = .004;
        val TLK_R_STP = -.004;
        var isUp = false;
        var TLK_L = TLK_L_DWN;
        var TLK_R = TLK_R_DWN;

        GAPI.bindArray({true}, {true}, { arrayOf(gamepad1.left_stick_x.toDouble(), -gamepad1.left_stick_y.toDouble(), gamepad1.right_stick_x.toDouble())}, {pw: Array<Double> -> run{wb.setAxisPower(pw[0], pw[1], pw[2])}});
        GAPI.bindVoid({true}, {gamepad1.right_bumper}, {sht.setMtPowerShooter(1.0)})
        GAPI.bindVoid({true}, {gamepad1.left_bumper}, {sht.setMtPowerShooter(0.0)})
        GAPI.bindDouble({true}, {true}, {gamepad1.right_trigger.toDouble() - gamepad1.left_trigger.toDouble()}, {pw: Double -> run{srt.setPwSort(pw)}})
        GAPI.bindVoid({true}, {hldr}, {grab.setMtPower(1.0)})
        GAPI.bindVoid({true}, {gamepad1.dpad_left}, {hldr = true})
        GAPI.bindVoid({true}, {!gamepad1.dpad_left && !gamepad1.dpad_up && !gamepad1.dpad_down && !hldr}, {grab.setMtPower(0.0)});
        GAPI.bindVoid({true}, {hldr}, {grab.setMtPower(1.0)})
        GAPI.bindVoid({true}, {gamepad1.dpad_up}, {grab.setMtPower(1.0); hldr=false})
        GAPI.bindVoid({true}, {gamepad1.dpad_down}, {grab.setMtPower(-1.0); hldr=false})
        GAPI.bindVoid({true}, {gamepad1.y}, {isUp = true; TLK_L = TLK_L_DWN; TLK_R = TLK_R_DWN})
        GAPI.bindVoid({true}, {gamepad1.b}, {srt.servDown()})
        GAPI.bindVoid({true}, {gamepad1.a}, {srt.servDown(); sht.setMtPowerShooter(-.6)});
        GAPI.bindVoid({true}, {isUp}, {
            TLK_L += TLK_L_STP;
            TLK_R += TLK_R_STP;
            srt.TLK_L.position = TLK_L;
            srt.TLK_R.position = TLK_R;
            if ( ( Math.abs(TLK_L - TLK_L_UP) < .05 ) || ( Math.abs(TLK_R - TLK_R_UP) < .05 ) ) {
                srt.servUp();
                isUp = false;
            }
        })
    }
}