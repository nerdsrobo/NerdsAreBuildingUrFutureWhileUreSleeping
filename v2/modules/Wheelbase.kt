package org.firstinspires.ftc.teamcode.v2.modules

import com.qualcomm.robotcore.hardware.DcMotor
import org.firstinspires.ftc.teamcode.v2.modules.superclasses.Module
import org.firstinspires.ftc.teamcode.v2.modules.superclasses.RobotPack
import org.firstinspires.ftc.teamcode.v2.util.dashconfigs.ConfigWheelbase.LB_CLOCKWISE
import org.firstinspires.ftc.teamcode.v2.util.dashconfigs.ConfigWheelbase.LF_CLOCKWISE
import org.firstinspires.ftc.teamcode.v2.util.dashconfigs.ConfigWheelbase.RB_CLOCKWISE
import org.firstinspires.ftc.teamcode.v2.util.dashconfigs.ConfigWheelbase.RF_CLOCKWISE

class Wheelbase(P: RobotPack) : Module(P) {
    val LB = P.hwmp.get(DcMotor::class.java, "LB")
    val RB = P.hwmp.get(DcMotor::class.java, "RB")
    val LF = P.hwmp.get(DcMotor::class.java, "LF")
    val RF = P.hwmp.get(DcMotor::class.java, "RB")

    val YAencMotor = RB;
    val YBencMotor = LF;
    val XencMotor = RF;
    val notEncMotor = LB;

    init {
        LB.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        RB.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        LF.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        RF.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE

        notEncMotor.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER;
        YAencMotor.mode = DcMotor.RunMode.RUN_USING_ENCODER;
        YBencMotor.mode = DcMotor.RunMode.RUN_USING_ENCODER;
        XencMotor.mode = DcMotor.RunMode.RUN_USING_ENCODER;
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

    class OdometryPhies(val YA: Int, val YB: Int, val X: Int) {}

    fun resetOdometry() {
        YAencMotor.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER;
        YAencMotor.mode = DcMotor.RunMode.RUN_USING_ENCODER;
        YBencMotor.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER;
        YBencMotor.mode = DcMotor.RunMode.RUN_USING_ENCODER;
        XencMotor.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER;
        XencMotor.mode = DcMotor.RunMode.RUN_USING_ENCODER;
    }

    fun getOdometryPhies(): OdometryPhies {
        return OdometryPhies(YAencMotor.currentPosition, YBencMotor.currentPosition, XencMotor.currentPosition);
    }
}