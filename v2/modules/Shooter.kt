package org.firstinspires.ftc.teamcode.v2.modules

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.Servo
import org.firstinspires.ftc.teamcode.v2.modules.superclasses.Module
import org.firstinspires.ftc.teamcode.v2.modules.superclasses.RobotPack

class Shooter(P: RobotPack): Module(P) {
    val SHT = P.hwmp.get(DcMotor::class.java, "SHT");

    val ShtR = P.hwmp.get(Servo::class.java, "ShtR");
    val ShtL = P.hwmp.get(Servo::class.java, "ShtL")

    init {
        SHT.mode = DcMotor.RunMode.RUN_USING_ENCODER;
        SHT.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.FLOAT;
    }

    val kSHT = -1.0;

    fun setShtPower(pw: Double) {
        SHT.power = pw * kSHT;
    }

    fun getShtTicks(): Int {
        return SHT.currentPosition;
    }

    fun setAnglePos(posL: Double, posR: Double) {
        ShtR.position = posR
        ShtL.position = posL;
    }

}