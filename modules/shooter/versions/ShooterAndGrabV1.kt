package org.firstinspires.ftc.teamcode.modules.shooter.versions

import org.firstinspires.ftc.teamcode.modules.shooter.ShooterAndGrab
import org.firstinspires.ftc.teamcode.modules.superclasses.RobotPack

class ShooterAndGrabV1(P: RobotPack) : ShooterAndGrab(P) {
    fun setMtPowerGrab(pw: Double) {
        LG.power = pw*kLG;
        RG.power = pw*kRG;
    }
    fun setMtPowerConv(pw: Double) {
        LC.power = pw*kLC;
        RC.power = pw*kRC;
    }

    companion object {
        const val kRC = -1*.5*-1;
        const val kLC = 1*.5*-1;
        const val kRG = -1*-1;
        const val kLG = 1*-1;
    }
}