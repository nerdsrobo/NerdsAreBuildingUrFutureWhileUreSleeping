package org.firstinspires.ftc.teamcode.modules.grab

import com.qualcomm.robotcore.hardware.DcMotor
import org.firstinspires.ftc.teamcode.modules.superclasses.Module
import org.firstinspires.ftc.teamcode.modules.superclasses.RobotPack

abstract class GrabSingle(P: RobotPack) : Module(P) {
    val GRB = P.hwmp.get(DcMotor::class.java, "GRB");
}