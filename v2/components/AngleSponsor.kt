package org.firstinspires.ftc.teamcode.v2.components

import org.firstinspires.ftc.teamcode.v2.components.superclasses.ProgramComponent
import org.firstinspires.ftc.teamcode.v2.components.util.UsefulFuncs.Companion.constrTo180
import org.firstinspires.ftc.teamcode.v2.modules.Imu
import org.firstinspires.ftc.teamcode.v2.modules.superclasses.RobotPack

class AngleSponsor(P: RobotPack): ProgramComponent(P) {

    private val imu = requestModule(Imu::class) as Imu;

    var angleOffset = 0.0;

    fun getCurrAngle(): Double {
        return constrTo180(imu.getAngle().toDouble() + angleOffset)
    }

    override fun tick() {

    }
}