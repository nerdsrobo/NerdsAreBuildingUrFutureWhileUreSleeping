package org.firstinspires.ftc.teamcode.v2.modules

import com.qualcomm.robotcore.hardware.DcMotor
import org.firstinspires.ftc.teamcode.v2.modules.superclasses.Module
import org.firstinspires.ftc.teamcode.v2.modules.superclasses.RobotPack

class Grab(P: RobotPack) : Module(P) {
    val GRB = P.hwmp.get(DcMotor::class.java, "GRB");

    init {
        GRB.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE;
        GRB.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER;
    }

    val kGrb = 1.0;

    fun setGrbPower(pw: Double) {
        GRB.power = pw * kGrb;
    }
}