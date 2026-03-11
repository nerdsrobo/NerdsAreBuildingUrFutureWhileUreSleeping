package org.firstinspires.ftc.teamcode.v2.systems.superclasses

import org.firstinspires.ftc.teamcode.v2.OnStateHandler
import org.firstinspires.ftc.teamcode.v2.components.superclasses.ProgramComponent
import org.firstinspires.ftc.teamcode.v2.modules.superclasses.Module
import org.firstinspires.ftc.teamcode.v2.modules.superclasses.RobotPack
import kotlin.reflect.KClass

abstract class ProgramSystem(val P: RobotPack) {

    init {
        P.linkerApi.addSystem(this);
    }

    fun <T: ProgramComponent>requestComponent(component: KClass<T>): ProgramComponent? {
        return P.linkerApi.requestComponent(component);
    }

    fun <T: Module>requestModule(module: KClass<T>): Module? {
        return P.linkerApi.requestModule(module);
    }

    var state: String = "default"
        get() = field
        set(value) {
            for ( handler in onStateHandlers ) {
                if ( value == handler.stateName ) {
                    handler.func(value);
                }
            }
            field = value;
        };
    private var onStateHandlers: ArrayList<OnStateHandler> = arrayListOf();

    fun onState(stateName: String, handler: ( stateName: String ) -> Unit ) {
        onStateHandlers.add(OnStateHandler(stateName, handler))
    }

    abstract fun tick();

}