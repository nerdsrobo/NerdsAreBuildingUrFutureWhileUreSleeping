package org.firstinspires.ftc.teamcode.v2.components

import org.firstinspires.ftc.teamcode.v2.components.superclasses.ProgramComponent
import org.firstinspires.ftc.teamcode.v2.modules.Wheelbase
import org.firstinspires.ftc.teamcode.v2.modules.superclasses.RobotPack

class Odometry(P: RobotPack) : ProgramComponent(P) {
    var wb: Wheelbase = requestModule(Wheelbase::class) as Wheelbase;

    override fun tick() {
        TODO("Not yet implemented")
    }
}