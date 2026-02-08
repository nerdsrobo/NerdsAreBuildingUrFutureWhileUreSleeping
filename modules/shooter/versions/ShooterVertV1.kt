package org.firstinspires.ftc.teamcode.modules.shooter.versions

import com.acmerobotics.dashboard.config.Config
import org.firstinspires.ftc.teamcode.modules.shooter.ShooterSingleMotor
import org.firstinspires.ftc.teamcode.modules.shooter.ShooterVert
import org.firstinspires.ftc.teamcode.modules.superclasses.RobotPack

@Config
class ShooterVertV1(P: RobotPack) : ShooterVert(P) {
    fun setMtPowerShooter(pw: Double) {
        SHT.power = pw*kSHT;
    }
    companion object {
        const val kSHT = -.82578087798980;
    }
}