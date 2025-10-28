package org.firstinspires.ftc.teamcode.scenes.teleop.test

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.modules.lift.versions.TwoMotorLiftV1
import org.firstinspires.ftc.teamcode.modules.shooter.versions.ShooterAndGrabV1
import org.firstinspires.ftc.teamcode.modules.wheelbase.versions.WheelbaseMecanumV1
import org.firstinspires.ftc.teamcode.scenes.superclasses.TeleOpBuilder

@TeleOp(name = "test1")
class test1 : TeleOpBuilder() {
    val wb = WheelbaseMecanumV1(P);
    val grab = ShooterAndGrabV1(P);
    val lift = TwoMotorLiftV1(P);
    init {
        GAPI.bind({true}, {true}, { arrayOf(gamepad1.left_stick_x.toDouble(), gamepad1.left_stick_y.toDouble(), gamepad1.right_stick_x.toDouble()) }, {pws: Array<Double> -> wb.setAxisPower(pws[0], pws[1], pws[2]) });
        GAPI.bind({true}, {gamepad1.left_bumper}, {grab.setMtPower(-1.0)});
        GAPI.bind({true}, {gamepad1.right_bumper}, {grab.setMtPower((1.0))});
        GAPI.bind({true}, {true}, { gamepad1.left_trigger.toDouble() }, { pw: Double -> lift.setMtPower(-pw)});
        GAPI.bind({true}, {true}, {gamepad1.right_trigger.toDouble()}, { pw: Double -> lift.setMtPower(pw)});
    }
}