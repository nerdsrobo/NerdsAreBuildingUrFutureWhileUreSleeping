package org.firstinspires.ftc.teamcode.v2.scenes.teleop.field

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.v2.modules.Imu
import org.firstinspires.ftc.teamcode.v2.modules.ShooterV2
import org.firstinspires.ftc.teamcode.v2.modules.Sorter
import org.firstinspires.ftc.teamcode.v2.modules.TolkalkaV2
import org.firstinspires.ftc.teamcode.v2.modules.Wheelbase
import org.firstinspires.ftc.teamcode.v2.modules.superclasses.RobotPack
import org.firstinspires.ftc.teamcode.v2.scenes.superclasses.TeleOpPacker

@TeleOp(name = "Опять ты")
class OpyatTyu(): TeleOpPacker() {
    override fun init_(P: RobotPack) {
        val wb = Wheelbase(P);
        //val imu = Imu(P);
        val srt = Sorter(P);

        val sht = ShooterV2(P)
        val tlk = TolkalkaV2(P)

        linkerApi.onOpModeStart {
            srt.closeGrab();
        }

        linkerApi.bindVoid({linkerApi.state == "default"}, {gamepad1.b}, {tlk.setServPower(-1.0)});
        linkerApi.bindVoid({linkerApi.state == "default"}, {!gamepad1.b}, {tlk.setServPower(0.0)});

        linkerApi.bindFlag({linkerApi.state == "default"}, {gamepad1.right_bumper}, {linkerApi.state = "shoot"});
        linkerApi.bindFlag({linkerApi.state == "shoot"}, {gamepad1.left_bumper}, {linkerApi.state = "default"})

        linkerApi.bindVoid({linkerApi.state == "default"}, {true}, {sht.setShtPower(-.3)})
        linkerApi.bindVoid({linkerApi.state == "shoot"}, {true}, {sht.setShtPower(1.0)});

        linkerApi.bindVoid({linkerApi.state == "shoot"}, {gamepad1.y}, {tlk.setServPower(1.0)});
        linkerApi.bindVoid({linkerApi.state == "shoot"}, {!gamepad1.y}, {tlk.setServPower(0.0)});

        linkerApi.bindArray({true}, {true}, { arrayOf(gamepad1.left_stick_x.toDouble(), gamepad1.left_stick_y.toDouble(), gamepad1.right_stick_x.toDouble())}, {pw: Array<Double> -> run{wb.setAxisPower(pw[0], pw[1], pw[2])}});
        linkerApi.bindDouble({true}, {true}, {(gamepad1.right_trigger - gamepad1.left_trigger).toDouble()}, {pw -> srt.setSrtPower(pw)});

    }
}