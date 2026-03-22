package org.firstinspires.ftc.teamcode.v2.scenes.teleop.test.component

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.v2.components.util.Alliance
import org.firstinspires.ftc.teamcode.v2.modules.superclasses.RobotPack
import org.firstinspires.ftc.teamcode.v2.scenes.superclasses.TeleOpPacker

@TeleOp(name = "COMPONENT - LookAtGoalCalcs - BLUE", group = "component")
class ComponentLookAtGoalCalcsBlue(): ComponentLookAtGoalCalcs(Alliance.BLUE) {}

@TeleOp(name = "COMPONENT - LookAtGoalCalcs - RED", group = "component")
class ComponentLookAtGoalCalcsRed(): ComponentLookAtGoalCalcs(Alliance.RED) {}

open class ComponentLookAtGoalCalcs(alliance: Alliance): TeleOpPacker(alliance) {
    override fun init_(P: RobotPack) {
        TODO("Not yet implemented")
    }
}