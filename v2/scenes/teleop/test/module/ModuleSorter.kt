package org.firstinspires.ftc.teamcode.v2.scenes.teleop.test.module

import com.acmerobotics.dashboard.FtcDashboard
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.v2.modules.Sorter
import org.firstinspires.ftc.teamcode.v2.modules.TolkalkaV2
import org.firstinspires.ftc.teamcode.v2.modules.superclasses.RobotPack
import org.firstinspires.ftc.teamcode.v2.scenes.superclasses.TeleOpPacker

@TeleOp(name = "MODULE - Sorter", group = "module")
class ModuleSorter(): TeleOpPacker() {
    override fun init_(P: RobotPack) {
        val dashTele = FtcDashboard.getInstance().telemetry;

        val srt = Sorter(P);
        val tlk = TolkalkaV2(P);

        linkerApi.bindVoid({true}, {true}, {
            dashTele.addData("enc", srt.getSrtTicks());
            dashTele.addData("btn", srt.getBtnState());
            dashTele.update();
        });
        linkerApi.bindDouble({true}, {true}, {(gamepad1.right_trigger - gamepad1.left_trigger).toDouble()}, {pw -> srt.setSrtPower(pw)});
        linkerApi.bindVoid({true}, {gamepad1.dpad_up}, {tlk.setServPower(1.0)});
        linkerApi.bindVoid({true}, {gamepad1.dpad_down}, {tlk.setServPower(-1.0)});
        linkerApi.bindVoid({true}, {gamepad1.y}, {srt.closeSht()});
        linkerApi.bindVoid({true}, {gamepad1.x}, {srt.openSht()});
        linkerApi.bindVoid({true}, {gamepad1.b}, {srt.closeGrab()});
        linkerApi.bindVoid({true}, {gamepad1.a}, {srt.openGrab()});
    }
}