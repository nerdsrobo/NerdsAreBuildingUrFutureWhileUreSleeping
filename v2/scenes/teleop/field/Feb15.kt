package org.firstinspires.ftc.teamcode.v2.scenes.teleop.field

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import org.firstinspires.ftc.teamcode.v2.components.DaMode
import org.firstinspires.ftc.teamcode.v2.components.SorterController
import org.firstinspires.ftc.teamcode.v2.components.util.ArtefactColor
import org.firstinspires.ftc.teamcode.v2.modules.Grab
import org.firstinspires.ftc.teamcode.v2.modules.Shooter
import org.firstinspires.ftc.teamcode.v2.modules.Sorter
import org.firstinspires.ftc.teamcode.v2.modules.Tolkalka
import org.firstinspires.ftc.teamcode.v2.modules.Wheelbase
import org.firstinspires.ftc.teamcode.v2.modules.superclasses.RobotPack
import org.firstinspires.ftc.teamcode.v2.scenes.superclasses.TeleOpPacker
import org.firstinspires.ftc.teamcode.v2.util.Configurable
import kotlin.math.round

@TeleOp(name="бородавачный полу прайм")
class Feb15(): TeleOpPacker() {
    override fun init_(P: RobotPack) {
        val srt = Sorter(P);
        val wb = Wheelbase(P);
        val sht = Shooter(P);
        val tlk = Tolkalka(P);
        val grb = Grab(P);

        linkerApi.addModules(arrayListOf(srt, wb, sht, tlk, grb));

        val srtControl = SorterController(P);

        srt.SRT.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER;
        srt.SRT.mode = DcMotor.RunMode.RUN_USING_ENCODER;

        srtControl.daMode = DaMode.SHOOT;
        srtControl.changeDaMode(DaMode.GRAB)


        var hldr = false;

        //var reseted = false;

        linkerApi.bindVoid({true}, {true}, {srtControl.tick();})

        linkerApi.bindArray({true}, {true}, { arrayOf(gamepad1.left_stick_x.toDouble(), -gamepad1.left_stick_y.toDouble(), gamepad1.right_stick_x.toDouble())}, {pw: Array<Double> -> run{wb.setAxisPower(pw[0], pw[1], pw[2])}});

        linkerApi.bindVoid({true}, {hldr}, {grb.setGrbPower(1.0)})
        linkerApi.bindVoid({true}, {gamepad1.dpad_left}, {hldr = true})
        linkerApi.bindVoid({true}, {!gamepad1.dpad_left && !gamepad1.dpad_up && !gamepad1.dpad_down && !hldr}, {grb.setGrbPower(0.0)});
        linkerApi.bindVoid({true}, {gamepad1.dpad_up}, {grb.setGrbPower(1.0); hldr=false})
        linkerApi.bindVoid({true}, {gamepad1.dpad_down}, {grb.setGrbPower(-1.0); hldr=false})


        linkerApi.bindDouble({true}, {true}, {gamepad1.right_trigger.toDouble() - gamepad1.left_trigger.toDouble()}, {pw: Double -> run{srtControl.getToPosition(
            (round(pw)*-10).toInt()
        )}})

        //linkerApi.bindFlag({linkerApi.state == "default" && !reseted}, {gamepad1.x}, {srtControl.eatAndSwap(ArtefactColor.GREEN)});
        //linkerApi.bindFlag({linkerApi.state == "default" && reseted}, {gamepad1.x}, {srtControl.eatNotSwap(ArtefactColor.GREEN)});
        //linkerApi.bindFlag({linkerApi.state == "default" && !reseted}, {gamepad1.b}, {srtControl.eatAndSwap(ArtefactColor.PURPLE)});
        //linkerApi.bindFlag({linkerApi.state == "default" && reseted}, {gamepad1.b}, {srtControl.eatNotSwap(ArtefactColor.PURPLE)});

        linkerApi.bindFlag({true}, {gamepad1.a}, {srtControl.slotSwap(-1)});

        linkerApi.bindFlag({linkerApi.state == "default"}, {gamepad1.right_bumper}, {linkerApi.setState_("timed"); srtControl.changeDaMode(DaMode.SHOOT); srtControl.getToPosition(Configurable.LOLSIXSEVEN); linkerApi.setTimer(450, {linkerApi.setState_("shoot")})})

        linkerApi.onState("shoot", {sht.setShtPower(1.0)})

        linkerApi.bindFlag({linkerApi.state == "shoot"}, {gamepad1.left_bumper}, {linkerApi.setState_("timed"); sht.setShtPower(0.0); srtControl.changeDaMode(DaMode.GRAB); linkerApi.setTimer(450, {linkerApi.setState_("default")})});

        linkerApi.bindFlag({linkerApi.state == "shoot"}, {gamepad1.y}, {tlk.servoUp()});
        linkerApi.bindFlag({true}, {gamepad1.b}, {tlk.servoDown()});

    }

}