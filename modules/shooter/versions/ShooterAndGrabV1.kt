package org.firstinspires.ftc.teamcode.modules.shooter.versions

import com.acmerobotics.dashboard.config.Config
import org.firstinspires.ftc.teamcode.modules.shooter.ShooterAndGrab
import org.firstinspires.ftc.teamcode.modules.superclasses.RobotPack

@Config
class ShooterAndGrabV1(P: RobotPack) : ShooterAndGrab(P) {
    fun setMtPower(pw: Double) {
        LG.power = pw*kLG;
        RG.power = pw*kRG;
        LC.power = pw*kLC;
        RC.power = pw*kRC;
    }

    companion object {
        const val kRC = -1;
        const val kLC = 1;
        const val kRG = -1;
        const val kLG = 1;
    }
}