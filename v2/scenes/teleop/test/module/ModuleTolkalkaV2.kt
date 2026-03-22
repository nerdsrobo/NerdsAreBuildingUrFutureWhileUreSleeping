package org.firstinspires.ftc.teamcode.v2.scenes.teleop.test.module

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.v2.modules.ShooterV2
import org.firstinspires.ftc.teamcode.v2.modules.Sorter
import org.firstinspires.ftc.teamcode.v2.modules.TolkalkaV2
import org.firstinspires.ftc.teamcode.v2.modules.superclasses.RobotPack
import org.firstinspires.ftc.teamcode.v2.scenes.superclasses.TeleOpPacker

@TeleOp(name = "MODULE - TOLKALKA", group = "module")
class ModuleTolkalkaV2(): TeleOpPacker() {
    override fun init_(P: RobotPack) {


        val tlk = TolkalkaV2(P);
        val sht = ShooterV2(P);
        val srt = Sorter(P);

        linkerApi.bindVoid({true}, {gamepad1.y}, {sht.setShtPower(1.0)});
        linkerApi.bindVoid({true}, {gamepad1.b}, {sht.setShtPower(0.0)});
        linkerApi.bindDouble({true}, {!gamepad1.dpad_up && !gamepad1.dpad_down}, {gamepad1.left_stick_y.toDouble()}, {pw -> tlk.TLK_L.power = pw});
        linkerApi.bindDouble({true}, {!gamepad1.dpad_up && !gamepad1.dpad_down}, {gamepad1.right_stick_y.toDouble()}, {pw -> tlk.TLK_R.power = pw});
        linkerApi.bindVoid({true}, {gamepad1.dpad_up}, {tlk.setServPower(1.0)});
        linkerApi.bindVoid({true}, {gamepad1.dpad_down}, {tlk.setServPower(-1.0)});
        linkerApi.bindDouble({true}, {true}, {(gamepad1.right_trigger - gamepad1.left_trigger).toDouble()*.4}, {pw -> srt.setSrtPower(pw)})
    }
}