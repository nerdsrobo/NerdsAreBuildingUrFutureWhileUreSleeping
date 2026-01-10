package org.firstinspires.ftc.teamcode.modules.shooter

import com.qualcomm.robotcore.hardware.CRServo
import com.qualcomm.robotcore.hardware.DcMotor
import org.firstinspires.ftc.teamcode.modules.superclasses.Module
import org.firstinspires.ftc.teamcode.modules.superclasses.RobotPack

abstract class ShooterVert(P: RobotPack) : Module(P) {
    val SHT = P.hwmp.get(DcMotor::class.java, "SHT")
    init {
        SHT.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.FLOAT;
        SHT.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
    }
}