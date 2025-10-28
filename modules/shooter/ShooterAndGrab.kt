package org.firstinspires.ftc.teamcode.modules.shooter

import com.qualcomm.robotcore.hardware.CRServo
import com.qualcomm.robotcore.hardware.DcMotor
import org.firstinspires.ftc.teamcode.modules.superclasses.Module
import org.firstinspires.ftc.teamcode.modules.superclasses.RobotPack

abstract class ShooterAndGrab(P: RobotPack) : Module(P) {
    val LG = P.hwmp.get(DcMotor::class.java, "LG");
    val RG = P.hwmp.get(DcMotor::class.java, "RG");
    val LC = P.hwmp.get(CRServo::class.java, "LC");
    val RC = P.hwmp.get(CRServo::class.java, "RC");
}