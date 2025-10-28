package org.firstinspires.ftc.teamcode.modules.lift.versions

import org.firstinspires.ftc.teamcode.modules.lift.TwoMotorLift
import org.firstinspires.ftc.teamcode.modules.superclasses.RobotPack

class TwoMotorLiftV1(P: RobotPack) : TwoMotorLift(P) {
    fun setMtPower(pw: Double) {
        LL.power = pw*kLL;
        RL.power = pw*kRL;
    }
    companion object {
        const val kLL = 1;
        const val kRL = -1;
    }
}