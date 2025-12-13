package org.firstinspires.ftc.teamcode.modules.shooter

import com.qualcomm.robotcore.hardware.CRServo
import com.qualcomm.robotcore.hardware.DcMotor
import org.firstinspires.ftc.teamcode.modules.superclasses.Module
import org.firstinspires.ftc.teamcode.modules.superclasses.RobotPack

abstract class ShooterSingleMotor(P: RobotPack) : Module(P) {
    val SHT = P.hwmp.get(DcMotor::class.java, "SHT");
    val HLD = P.hwmp.get(CRServo::class.java, "HLD");
    val TLK = P.hwmp.get(CRServo::class.java, "TLK");
}