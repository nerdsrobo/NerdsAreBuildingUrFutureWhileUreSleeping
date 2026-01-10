package org.firstinspires.ftc.teamcode.modules.sort

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.Servo
import org.firstinspires.ftc.teamcode.modules.superclasses.Module
import org.firstinspires.ftc.teamcode.modules.superclasses.RobotPack

abstract class SortSingleAndServos(P: RobotPack) : Module(P) {
    val SRT = P.hwmp.get(DcMotor::class.java, "SRT");
    val TLK_L = P.hwmp.get(Servo::class.java, "TLK_L");
    val TLK_R = P.hwmp.get(Servo::class.java, "TLK_R")
    val HLD = P.hwmp.get(Servo::class.java, "HLD")
}