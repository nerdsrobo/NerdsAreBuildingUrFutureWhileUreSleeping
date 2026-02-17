package org.firstinspires.ftc.teamcode.v2.components.superclasses

import org.firstinspires.ftc.teamcode.v2.modules.Wheelbase
import org.firstinspires.ftc.teamcode.v2.modules.superclasses.Module
import org.firstinspires.ftc.teamcode.v2.modules.superclasses.RobotPack
import kotlin.reflect.KClass

abstract class ProgramComponent(P: RobotPack) {
    val P = P;
    fun <T: Module>requestModule(moduleClass: KClass<T>): Module? {
        return P.linkerApi.requestModule(moduleClass);
    }

    abstract fun tick();
}