package org.firstinspires.ftc.teamcode.modules.grab.versions

import org.firstinspires.ftc.teamcode.modules.grab.GrabSingle
import org.firstinspires.ftc.teamcode.modules.shooter.ShooterAndGrab
import org.firstinspires.ftc.teamcode.modules.superclasses.RobotPack

class GrabSingleV1(P: RobotPack) : GrabSingle(P) {
    fun setMtPowerServos(pw: Double) {
        GRB.power = pw*kGRB;
    }

    companion object {
        const val kGRB = 1;
    }
}