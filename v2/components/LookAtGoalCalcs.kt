package org.firstinspires.ftc.teamcode.v2.components

import org.firstinspires.ftc.teamcode.v2.components.superclasses.ProgramComponent
import org.firstinspires.ftc.teamcode.v2.components.util.Alliance
import org.firstinspires.ftc.teamcode.v2.components.util.Calculations
import org.firstinspires.ftc.teamcode.v2.components.util.UsefulFuncs
import org.firstinspires.ftc.teamcode.v2.modules.superclasses.RobotPack
import org.firstinspires.ftc.teamcode.v2.util.dashconfigs.ConfigLookAtGoalCalcs

class LookAtGoalCalcs(P: RobotPack): ProgramComponent(P) {

    class LookAtGoalCalcsResult(val rotRobot: Double, val shooterAngle: Double) {}

    fun calcs(absX: Double, absY: Double, wheelW: Double, isNear: Boolean): LookAtGoalCalcsResult {
        if ( P.alliance == Alliance.BLUE ) {
            return LookAtGoalCalcsResult(Calculations.pointsAngle(Calculations.Coords(absX, absY), Calculations.Coords(ConfigLookAtGoalCalcs.blueGoalPointX, ConfigLookAtGoalCalcs.blueGoalPointY)),
                Calculations.shootingTraectoryCalc(Calculations.Coords(absX, absY),
                    Calculations.Coords(ConfigLookAtGoalCalcs.blueGoalPointX, ConfigLookAtGoalCalcs.blueGoalPointY),
                    ConfigLookAtGoalCalcs.goalPointHeight, wheelW,
                    if (isNear) ConfigLookAtGoalCalcs.nearZoneIs1stParabolaPoint else ConfigLookAtGoalCalcs.farZoneIs1stParabolaPoint))
        }
        return LookAtGoalCalcsResult(
            UsefulFuncs.constrTo180(Calculations.pointsAngle(Calculations.Coords(absX, absY), Calculations.Coords(ConfigLookAtGoalCalcs.redGoalPointX, ConfigLookAtGoalCalcs.redGoalPointY))+180),
            Calculations.shootingTraectoryCalc(Calculations.Coords(absX, absY),
                Calculations.Coords(ConfigLookAtGoalCalcs.redGoalPointX, ConfigLookAtGoalCalcs.redGoalPointY),
                ConfigLookAtGoalCalcs.goalPointHeight, wheelW,
                if (isNear) ConfigLookAtGoalCalcs.nearZoneIs1stParabolaPoint else ConfigLookAtGoalCalcs.farZoneIs1stParabolaPoint))
    }

    override fun tick() {

    }
}