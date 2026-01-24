package org.firstinspires.ftc.teamcode.modules.wheelbase.versions

import org.firstinspires.ftc.teamcode.modules.superclasses.RobotPack
import org.firstinspires.ftc.teamcode.modules.wheelbase.WheelbaseSuper4Wheel

class WheelbaseMecanumV1(P: RobotPack) : WheelbaseSuper4Wheel(P) {
    fun setMtPower(rb: Double, rf: Double, lb: Double, lf: Double) {
        RB.power = rb;
        RF.power = rf;
        LB.power = lb;
        LF.power = lf;
    }
    fun setAxisPower(pwX: Double, pwY: Double, pwRot: Double) {
        setMtPower(
            -pwX - pwY + pwRot,
            pwX - pwY + pwRot,
            pwX + pwY + pwRot,
            -pwX + pwY + pwRot
        );
    }
}