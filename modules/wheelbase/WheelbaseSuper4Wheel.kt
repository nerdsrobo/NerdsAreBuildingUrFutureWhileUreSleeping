package org.firstinspires.ftc.teamcode.modules.wheelbase

import com.qualcomm.robotcore.hardware.DcMotor
import org.firstinspires.ftc.teamcode.modules.superclasses.Module
import org.firstinspires.ftc.teamcode.modules.superclasses.RobotPack

abstract class WheelbaseSuper4Wheel(P: RobotPack) : Module(P) {
    val RF = P.hwmp.get(DcMotor::class.java, "RF");
    val RB = P.hwmp.get(DcMotor::class.java, "RB");
    val LF = P.hwmp.get(DcMotor::class.java, "LF");
    val LB = P.hwmp.get(DcMotor::class.java, "LB");
    override fun initModule() {

    }
}