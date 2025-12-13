package org.firstinspires.ftc.teamcode.modules.shooter.versions

import com.acmerobotics.dashboard.config.Config
import org.firstinspires.ftc.teamcode.modules.shooter.ShooterSingleMotor
import org.firstinspires.ftc.teamcode.modules.superclasses.RobotPack

@Config
class ShooterSingleMotorV1(P: RobotPack) : ShooterSingleMotor(P) {
    fun setMtPowerShooter(pw: Double) {
        SHT.power = pw*kSHT;
    }
    fun setMtPowerServos(pwHLD: Double, pwTLK: Double) {
        HLD.power = pwHLD*kHLD;
        TLK.power = pwTLK*kTLK;
    }

    companion object {
        const val kSHT = 1;
        const val kHLD = 1;
        const val kTLK = 1;
    }
}