package org.firstinspires.ftc.teamcode.scenes.teleop.test

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.modules.lift.versions.TwoMotorLiftV1
import org.firstinspires.ftc.teamcode.modules.shooter.versions.ShooterAndGrabV1
import org.firstinspires.ftc.teamcode.modules.shooter.versions.ShooterSingleMotorV1
import org.firstinspires.ftc.teamcode.modules.superclasses.RobotPack
import org.firstinspires.ftc.teamcode.modules.wheelbase.versions.WheelbaseMecanumV1
import org.firstinspires.ftc.teamcode.scenes.superclasses.TeleOpBuilder
import kotlin.math.abs

@TeleOp(name = "нет ничего более вечного, чем временное", group="test")
class test1 : TeleOpBuilder() {
    //val wb = WheelbaseMecanumV1(P);
    //val grab = ShooterAndGrabV1(P);
    //val lift = TwoMotorLiftV1(P);
    //init {
    //GAPI.bindArray({true}, {true}, { arrayOf(gamepad1.left_stick_x.toDouble(), gamepad1.left_stick_y.toDouble(), gamepad1.right_stick_x.toDouble()) }, {pws: Array<Double> -> wb.setAxisPower(pws[0], pws[1], pws[2]) });
    //GAPI.bindVoid({true}, {gamepad1.left_bumper}, {grab.setMtPower(-1.0)});
    //GAPI.bindVoid({true}, {gamepad1.right_bumper}, {grab.setMtPower((1.0))});
    //GAPI.bindDouble({true}, {true}, { gamepad1.left_trigger.toDouble() }, { pw: Double -> lift.setMtPower(-pw)});
    //GAPI.bindDouble({true}, {true}, {gamepad1.right_trigger.toDouble()}, { pw: Double -> lift.setMtPower(pw)});
    //}
    override fun init_(P: RobotPack) {
        //val wb = WheelbaseMecanumV1(P);
        //GAPI.bindArray({true}, {true}, { arrayOf(gamepad1.left_stick_x.toDouble(), gamepad1.left_stick_y.toDouble(), gamepad1.right_stick_x.toDouble()) }, {pws: Array<Double> -> wb.setAxisPower(pws[0], pws[1], pws[2]) });
        //val grab = ShooterAndGrabV1(P);
        //GAPI.bindVoid({true}, {gamepad1.left_bumper}, {grab.setMtPowerGrab(1.0)});
        //GAPI.bindVoid({true}, {gamepad1.right_bumper}, {grab.setMtPowerGrab(-1.0)});
        //GAPI.bindVoid({true}, {!gamepad1.left_bumper && !gamepad1.right_bumper && abs(gamepad1.left_stick_y) < .05}, {grab.setMtPowerGrab(0.0)});
        //GAPI.bindVoid({true}, {gamepad1.dpad_up}, {grab.setMtPowerConv(1.0)});
        //GAPI.bindVoid({true}, {gamepad1.y}, {grab.setMtPowerConv(-1.0)});
        //GAPI.bindVoid({true}, {!gamepad1.dpad_up && !gamepad1.y}, {grab.setMtPowerConv(0.0)});
        //val lift = TwoMotorLiftV1(P);
        //GAPI.bindDouble({true}, {true}, {(gamepad1.left_trigger-gamepad1.right_trigger).toDouble()}, {pw: Double -> lift.setMtPower(pw)});

        //GAPI.bindDouble({true}, {true}, {gamepad1.left_stick_y.toDouble()}, {pw: Double -> grab.setMtPowerGrab(pw)});

        //GAPI.bindDouble({true}, {true}, {gamepad1.left_stick_y.toDouble()}, {pw: Double -> grab.setMtPowerConv(pw)});

        val wb = WheelbaseMecanumV1(P);
        GAPI.bindArray({true}, {true}, { arrayOf(gamepad1.left_stick_x.toDouble(), -gamepad1.left_stick_y.toDouble(), gamepad1.right_stick_x.toDouble()) }, { pws: Array<Double> -> wb.setAxisPower(pws[0], pws[1], pws[2]) });

        val grab = ShooterSingleMotorV1(P);
        GAPI.bindVoid({true}, {gamepad1.left_bumper}, {grab.setMtPowerShooter(-.7)});
        GAPI.bindVoid({true}, {gamepad1.right_bumper}, {grab.setMtPowerShooter(1.0)});
        GAPI.bindArray({true}, {true}, { arrayOf((if (gamepad1.a) 1.0 else 0.0) + (if (gamepad1.x) -1.0 else 0.0), (if (gamepad1.b) 1.0 else 0.0) + (if (gamepad1.y) -1.0 else 0.0)) }, { pws: Array<Double> -> telemetry.addData("asd", pws[0]); telemetry.update(); grab.setMtPowerServos(pws[0], pws[1]) })
        GAPI.bindDouble({true}, {!gamepad1.left_bumper && !gamepad1.right_bumper}, {gamepad1.right_trigger.toDouble()}, {pw: Double -> grab.setMtPowerShooter(pw)});
        GAPI.bindVoid({true}, {!gamepad1.left_bumper && !gamepad1.right_bumper && abs(gamepad1.right_trigger) < .0789}, {grab.setMtPowerShooter(0.0)});

    }
}