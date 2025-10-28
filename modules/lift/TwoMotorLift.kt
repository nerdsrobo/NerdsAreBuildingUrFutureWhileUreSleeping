package org.firstinspires.ftc.teamcode.modules.lift

import com.qualcomm.robotcore.hardware.DcMotor
import org.firstinspires.ftc.teamcode.modules.superclasses.Module
import org.firstinspires.ftc.teamcode.modules.superclasses.RobotPack

abstract class TwoMotorLift(P: RobotPack) : Module(P) {
    val LL = P.hwmp.get(DcMotor::class.java, "LL");
    val RL = P.hwmp.get(DcMotor::class.java, "RL");
}