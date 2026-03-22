package org.firstinspires.ftc.teamcode.v2.scenes.teleop.test.module

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.v2.modules.Grab
import org.firstinspires.ftc.teamcode.v2.modules.superclasses.RobotPack
import org.firstinspires.ftc.teamcode.v2.scenes.superclasses.TeleOpPacker

@TeleOp(name = "MODULE - Grab", group = "module")
class ModuleGrab(): TeleOpPacker() {
    override fun init_(P: RobotPack) {
        val grb = Grab(P);

        linkerApi.bindDouble({true}, {!gamepad1.dpad_up && !gamepad1.dpad_down}, {-gamepad1.left_stick_y.toDouble()}, {pw -> grb.GRB.power = pw});
        linkerApi.bindVoid({true}, {gamepad1.dpad_up}, {grb.setGrbPower(1.0)});
        linkerApi.bindVoid({true}, {gamepad1.dpad_down}, {grb.setGrbPower(-1.0)});
    }
}