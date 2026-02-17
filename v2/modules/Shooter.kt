package org.firstinspires.ftc.teamcode.v2.modules

import com.qualcomm.robotcore.hardware.DcMotor
import org.firstinspires.ftc.teamcode.v2.modules.superclasses.Module
import org.firstinspires.ftc.teamcode.v2.modules.superclasses.RobotPack

class Shooter(P: RobotPack): Module(P) {
    val SHT = P.hwmp.get(DcMotor::class.java, "SHT");

    init {
        SHT.mode = DcMotor.RunMode.RUN_USING_ENCODER;
        SHT.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.FLOAT;
    }

    val kSHT = -1.0;

    fun setShtPower(pw: Double) {
        SHT.power = pw * kSHT;
    }

}