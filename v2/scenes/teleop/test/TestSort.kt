package org.firstinspires.ftc.teamcode.v2.scenes.teleop.test

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import org.firstinspires.ftc.teamcode.v2.components.DaMode
import org.firstinspires.ftc.teamcode.v2.components.SorterController
import org.firstinspires.ftc.teamcode.v2.modules.Shooter
import org.firstinspires.ftc.teamcode.v2.modules.superclasses.RobotPack
import org.firstinspires.ftc.teamcode.v2.modules.Sorter
import org.firstinspires.ftc.teamcode.v2.modules.Wheelbase
import org.firstinspires.ftc.teamcode.v2.scenes.superclasses.TeleOpPacker

@TeleOp(name = "prrrrrrrrrrrrrrrrrriiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiimmmmmmmmmmmmmeee", group = "test")
class TestSort() : TeleOpPacker() {
    override fun init_(P: RobotPack) {
        val srt = Sorter(P);
        val wb = Wheelbase(P);
        val sht = Shooter(P);

        linkerApi.addModules(arrayListOf(srt));

        val srtControl = SorterController(P);

        srt.SRT.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER;
        srt.SRT.mode = DcMotor.RunMode.RUN_USING_ENCODER;

        //linkerApi.bindVoid({true}, {true}, {telemetry.addData("a", srt.getSrtTicks()); telemetry.addData("b", System.currentTimeMillis()); telemetry.addData("c", sht.SHT.currentPosition); telemetry.update()});
        //linkerApi.bindDouble({true}, {true}, {((gamepad1.right_trigger - gamepad1.left_trigger).toDouble())}, { pw: Double -> srt.setSrtPower(pw) })

        linkerApi.bindVoid({true}, {true}, {srtControl.tick();})// telemetry.addData("srt", srt.getSrtTicks()); telemetry.addData("target", srtControl.targetTicks); telemetry.update();});
        linkerApi.bindFlag({true}, {gamepad1.a}, {srtControl.slotSwap(-1)})
        linkerApi.bindFlag({true}, {gamepad1.x}, {srtControl.changeDaMode(DaMode.SHOOT)});
        linkerApi.bindFlag({true}, {gamepad1.y}, {srtControl.changeDaMode(DaMode.GRAB)});

        linkerApi.bindDouble({true}, {true}, {gamepad1.right_trigger.toDouble() - gamepad1.left_trigger.toDouble()}, {pw: Double -> run{srt.setSrtPower(pw)}})

    }
}