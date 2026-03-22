package org.firstinspires.ftc.teamcode.v2

import org.firstinspires.ftc.teamcode.v2.components.superclasses.ProgramComponent
import org.firstinspires.ftc.teamcode.v2.modules.superclasses.Module
import org.firstinspires.ftc.teamcode.v2.systems.superclasses.ProgramSystem
import kotlin.reflect.KClass

class VoidHandler(val condition: () -> Boolean, val conditionButton: () -> Boolean, val func: () -> Unit) {}
class DoubleHandler(val condition: () -> Boolean, val conditionButton: () -> Boolean, val doubleGetter: () -> Double, val func: (Double) -> Unit) {}
class ArrayDoubleHandler(val condition: () -> Boolean, val conditionButton: () -> Boolean, val arrayGetter: () -> Array<Double>, val func: (Array<Double>) -> Unit) {}
class OnStateHandler(val stateName: String, val func: (stateName: String) -> Unit) {}
class TimerHandler(val timeout: Long, val timer: Long, val func: () -> Unit) {}
class FlaggedButtonHandler(val condition: () -> Boolean, val conditionButton: () -> Boolean, val func: () -> Unit, var flag: Boolean) {}
class OnOpModeStartHandler(val func: () -> Unit) {}

class LinkerAPI {
    var state = "default"
        get() = field
        set(value) {
            for ( i in onStateHandlers.indices ) {
                if ( value == onStateHandlers[i].stateName ) {
                    onStateHandlers[i].func(value);
                }
            }
            field = value;
        }
    private var onStateHandlers: ArrayList<OnStateHandler> = arrayListOf();

    private var voidHandlers: ArrayList<VoidHandler> = arrayListOf();
    private var doubleHandlers: ArrayList<DoubleHandler> = arrayListOf();
    private var arrayDoubleHandlers: ArrayList<ArrayDoubleHandler> = arrayListOf();
    private var timersHandlers: ArrayList<TimerHandler> = arrayListOf();
    private var flaggedButtonHandlers: ArrayList<FlaggedButtonHandler> = arrayListOf();
    private var onOpModeStartHandlers: ArrayList<OnOpModeStartHandler> = arrayListOf();
    private var everyTicksHandlers: ArrayList<() -> Unit> = arrayListOf();

    fun tick() {
        for ( i in voidHandlers.indices ) {
            if ( voidHandlers[i].condition() && voidHandlers[i].conditionButton() ) {
                voidHandlers[i].func();
            }
        }
        for ( i in doubleHandlers.indices ) {
            if ( doubleHandlers[i].condition() && doubleHandlers[i].conditionButton() ) {
                doubleHandlers[i].func(doubleHandlers[i].doubleGetter());
            }
        }
        for ( i in arrayDoubleHandlers.indices ) {
            if ( arrayDoubleHandlers[i].condition() && arrayDoubleHandlers[i].conditionButton() ) {
                arrayDoubleHandlers[i].func(arrayDoubleHandlers[i].arrayGetter());
            }
        }
        for ( i in flaggedButtonHandlers.indices ) {
            if ( ( flaggedButtonHandlers[i].condition() && flaggedButtonHandlers[i].conditionButton() ) && !flaggedButtonHandlers[i].flag) {
                flaggedButtonHandlers[i].flag = true;
                flaggedButtonHandlers[i].func();
            }
            else if ( !( flaggedButtonHandlers[i].condition() && flaggedButtonHandlers[i].conditionButton() ) ) {
                flaggedButtonHandlers[i].flag = false;
            }
        }
        val time = System.currentTimeMillis();
//        for ( i in timersHandlers.indices ) {
//            if ( time - timersHandlers[i].timeout > timersHandlers[i].timer ) {
//                timersHandlers[i].func();
//                timersHandlers.removeAt(i);
//                break;
//            }
//        }
        val handlers = timersHandlers.iterator();
        while ( handlers.hasNext() ) {
            val handler = handlers.next();
            if ( time - handler.timeout > handler.timer ) {
                handler.func();
                handlers.remove();
            }
        }
        // for ( c in activeComponents ) {
        //     requestComponent(c)?.tick();
        // }
        for ( c in activeComponents + alwaysActiveComponents ) {
            c.tick();
        }
        for ( s in systems ) {
            s.tick();
        }
    }

    fun executeOnOpModeStartHandlers() {
        for ( handler in onOpModeStartHandlers ) {
            handler.func();
        }
    }


    fun bindVoid(condition: () -> Boolean, button: () -> Boolean, func: () -> Unit) {
        voidHandlers.add(VoidHandler(condition, button, func));
    }
    fun bindDouble(condition: () -> Boolean, button: () -> Boolean, doubleGetter: () -> Double, func: (Double) -> Unit) {
        doubleHandlers.add(DoubleHandler(condition, button, doubleGetter, func));
    }
    fun bindArray(condition: () -> Boolean, button: () -> Boolean, arrayGetter: () -> Array<Double>, func: (Array<Double>) -> Unit) {
        arrayDoubleHandlers.add(ArrayDoubleHandler(condition, button, arrayGetter, func));
    }
    fun bindFlag(condition: () -> Boolean, button: () -> Boolean, func: () -> Unit) {
        flaggedButtonHandlers.add(FlaggedButtonHandler(condition, button, func, false));
    }
    fun onState(state: String, func: (stateName: String) -> Unit) {
        onStateHandlers.add(OnStateHandler(state, func));
    }
    fun setTimer(timer: Long, func: () -> Unit) {
        timersHandlers.add(TimerHandler(System.currentTimeMillis(), timer, func));
    }
    fun onOpModeStart(func: () -> Unit) {
        onOpModeStartHandlers.add(OnOpModeStartHandler(func))
    }
    fun everyTick(func: () -> Unit) {
        everyTicksHandlers.add(func);
    }

    private var modules: ArrayList<Module> = arrayListOf();

    fun addModule(moduleToAdd: Module) {
        modules.add(moduleToAdd);
    }

    fun <T: Module>requestModule(module: KClass<T>): Module? {
        for ( m in modules ) {
            if ( m::class == module ) { return m; }
        }
        return null;
    }

    private var components: ArrayList<ProgramComponent> = arrayListOf();

    fun addComponent(componentToAdd: ProgramComponent) {
        components.add(componentToAdd);
    }

    fun <T: ProgramComponent>requestComponent(component: KClass<T>): ProgramComponent? {
        for ( c in components ) {
            if ( c::class == component ) { return c; }
        }
        return null;
    }

    private var systems: ArrayList<ProgramSystem> = arrayListOf();

    fun addSystem(systemToAdd: ProgramSystem) {
        systems.add(systemToAdd);
    }

    fun <T: ProgramSystem>requestSystem(system: KClass<T>): ProgramSystem? {
        for ( s in systems ) {
            if ( s::class == system ) { return s; }
        }
        return null;
    }

    // private var activeComponents: ArrayList<KClass<out ProgramComponent>> = arrayListOf();

    // fun activateComponents(components: Array<out ProgramComponent>) {
    //     activeComponents = arrayListOf();
    //     for ( c in components ) { activeComponents.add(c::class); }
    // }

    var activeComponents: ArrayList<ProgramComponent> = arrayListOf();
    var alwaysActiveComponents: ArrayList<ProgramComponent> = arrayListOf();

    fun activateComponents(components: ArrayList<ProgramComponent>) {
        activeComponents = components;
    }

}