package org.firstinspires.ftc.teamcode.v2.scenes.teleop.test.module

import com.acmerobotics.dashboard.FtcDashboard
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.v2.modules.Wheelbase
import org.firstinspires.ftc.teamcode.v2.modules.superclasses.RobotPack
import org.firstinspires.ftc.teamcode.v2.scenes.superclasses.TeleOpPacker
import org.firstinspires.ftc.teamcode.v2.util.dashconfigs.ConfigWheelbase

@TeleOp(name = "MODULE - Wheelbase", group = "module")
class ModuleWheelbase() : TeleOpPacker() {
    override fun init_(P: RobotPack) {
        val dashTelemetry = FtcDashboard.getInstance().telemetry;

        val wb = Wheelbase(P);

        P.linkerApi.bindVoid({true}, {gamepad1.y}, {wb.clockwiseTest()});

        P.linkerApi.bindArray({true}, {!gamepad1.dpad_up && !gamepad1.dpad_down && !gamepad1.dpad_right && !gamepad1.dpad_left}, { arrayOf(gamepad1.left_stick_x.toDouble(), gamepad1.left_stick_y.toDouble(), gamepad1.right_stick_x.toDouble()*.7) }, {pws -> wb.setAxisPower(pws[0], pws[1], pws[2])});

        P.linkerApi.bindVoid({true}, {true}, {dashTelemetry.addData("asd", gamepad1.left_stick_y); dashTelemetry.update()});

        P.linkerApi.bindVoid({true}, {gamepad1.dpad_up}, {wb.LF.power = .5 * ConfigWheelbase.LF_CLOCKWISE});
        P.linkerApi.bindVoid({true}, {gamepad1.dpad_right}, {wb.RF.power = .5 * ConfigWheelbase.RF_CLOCKWISE});
        P.linkerApi.bindVoid({true}, {gamepad1.dpad_down}, {wb.RB.power = .5 * ConfigWheelbase.RB_CLOCKWISE});
        P.linkerApi.bindVoid({true}, {gamepad1.dpad_left}, {wb.LB.power = .5 * ConfigWheelbase.LB_CLOCKWISE});
    }

}