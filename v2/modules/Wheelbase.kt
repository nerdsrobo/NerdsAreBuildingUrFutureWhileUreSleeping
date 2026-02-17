package org.firstinspires.ftc.teamcode.v2.modules

import com.qualcomm.robotcore.hardware.DcMotor
import org.firstinspires.ftc.teamcode.v2.modules.superclasses.Module
import org.firstinspires.ftc.teamcode.v2.modules.superclasses.RobotPack

class Wheelbase(P: RobotPack) : Module(P) {
    val LB = P.hwmp.get(DcMotor::class.java, "LB")
    val RB = P.hwmp.get(DcMotor::class.java, "RB")
    val LF = P.hwmp.get(DcMotor::class.java, "LF")
    val RF = P.hwmp.get(DcMotor::class.java, "RB")

    val LF_CLOCKWISE = 1.0;
    val RF_CLOCKWISE = 1.0;
    val LB_CLOCKWISE = 1.0;
    val RB_CLOCKWISE = 1.0;

    init {
        LB.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        RB.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        LF.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        RF.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE

        LB.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER;
        RB.mode = DcMotor.RunMode.RUN_USING_ENCODER;
        LF.mode = DcMotor.RunMode.RUN_USING_ENCODER;
        RF.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER;
    }

    fun setMtPower(pwLB: Double, pwRB: Double, pwLF: Double, pwRF: Double) {
        LB.power = pwLB;
        RB.power = pwRB;
        LF.power = pwLF;
        RF.power = pwRF;
    }

    fun setMtZero() { setMtPower(.0, .0, .0, .0) }

    fun clockwiseTest() {
        setMtPower(LB_CLOCKWISE, RB_CLOCKWISE, LF_CLOCKWISE, RF_CLOCKWISE)
    }

    fun setAxisPower(pwX: Double, pwY: Double, pwRot: Double) {
        setMtPower(
            LB_CLOCKWISE * (pwX - pwY - pwRot),
            RB_CLOCKWISE * (pwX + pwY - pwRot),
            LF_CLOCKWISE * (-pwX - pwY - pwRot),
            RF_CLOCKWISE * (-pwX + pwY -pwRot)
        )
    }
}