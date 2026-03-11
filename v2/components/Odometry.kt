package org.firstinspires.ftc.teamcode.v2.components

import org.firstinspires.ftc.teamcode.v2.components.superclasses.ProgramComponent
import org.firstinspires.ftc.teamcode.v2.components.util.Calculations
import org.firstinspires.ftc.teamcode.v2.modules.Wheelbase
import org.firstinspires.ftc.teamcode.v2.modules.superclasses.RobotPack
import org.firstinspires.ftc.teamcode.v2.util.dashconfigs.ConfigOdometry
import kotlin.math.atan
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

class Odometry(P: RobotPack) : ProgramComponent(P) {
    var wb: Wheelbase = requestModule(Wheelbase::class) as Wheelbase;

    val angleAtX = atan(ConfigOdometry.offsetXy/ConfigOdometry.offsetXx);

    var lastPhiYA: Int = 0;
    var lastPhiYB: Int = 0;
    var lastPhiX: Int = 0;

    var relX = .0;
    var relY = .0;
    var lastAngle: Double = .0;

    var calc = true;

    fun resetRelXY() {
        relX = .0;
        relY = .0;
    }

    override fun tick() {
        if ( calc ) {
            val phies = wb.getOdometryPhies();
            val rot =
                Calculations.calcRotationByOdometry(phies.YA - lastPhiYA, phies.YB - lastPhiYB);
            val My = rot.Ro * sin(rot.revs * Math.PI * 2);
            val phiXToRot =
                sqrt(ConfigOdometry.offsetXx.pow(2) + ConfigOdometry.offsetXy.pow(2)) * rot.revs * cos(angleAtX) / ConfigOdometry.deadWheelR;
            val Mx = (phies.X - lastPhiX - phiXToRot) * Math.PI * 2 * ConfigOdometry.deadWheelR;
            lastAngle += rot.revs * 360;
            val moves = Calculations.rotateCoordSystem(Mx, My, lastAngle);
            relX += moves.x;
            relY += moves.y;
        }
    }
}