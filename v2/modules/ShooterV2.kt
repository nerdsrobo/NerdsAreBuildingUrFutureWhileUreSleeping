package org.firstinspires.ftc.teamcode.v2.modules

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.Servo
import org.firstinspires.ftc.teamcode.v2.modules.superclasses.Module
import org.firstinspires.ftc.teamcode.v2.modules.superclasses.RobotPack
import org.firstinspires.ftc.teamcode.v2.util.dashconfigs.ConfigShooterV2.kSHT_L
import org.firstinspires.ftc.teamcode.v2.util.dashconfigs.ConfigShooterV2.kSHT_R

class ShooterV2(P: RobotPack) : Module(P) {
    val SHT_R = P.hwmp.get(DcMotor::class.java, "SHT_R");
    val SHT_L = P.hwmp.get(DcMotor::class.java, "SHT_L");

    val ShtR = P.hwmp.get(Servo::class.java, "ShtR");
    val ShtL = P.hwmp.get(Servo::class.java, "ShtL");

    init {
        SHT_R.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER;
        SHT_R.mode = DcMotor.RunMode.RUN_USING_ENCODER;
        SHT_R.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.FLOAT;
        SHT_L.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER;
        SHT_L.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.FLOAT;
    }

    fun setShtPower(pw: Double) {
        SHT_R.power = pw * kSHT_R;
        SHT_L.power = pw * kSHT_L;
    }

    fun getShtTicks(): Int {
        return SHT_R.currentPosition;
    }

    fun setAnglePos(posL: Double, posR: Double) {
        ShtR.position = posR
        ShtL.position = posL;
    }

}