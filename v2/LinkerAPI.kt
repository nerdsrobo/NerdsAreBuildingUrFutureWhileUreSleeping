package org.firstinspires.ftc.teamcode.v2

import org.firstinspires.ftc.teamcode.v2.modules.superclasses.Module
import kotlin.reflect.KClass

class VoidHandler(val condition: () -> Boolean, val conditionButton: () -> Boolean, val func: () -> Unit) {}
class DoubleHandler(val condition: () -> Boolean, val conditionButton: () -> Boolean, val doubleGetter: () -> Double, val func: (Double) -> Unit) {}
class ArrayDoubleHandler(val condition: () -> Boolean, val conditionButton: () -> Boolean, val arrayGetter: () -> Array<Double>, val func: (Array<Double>) -> Unit) {}
class OnStateHandler(val stateName: String, val func: (stateName: String) -> Unit) {}
class TimerHandler(val timeout: Long, val timer: Long, val func: () -> Unit) {}
class FlaggedButtonHandler(val condition: () -> Boolean, val conditionButton: () -> Boolean, val func: () -> Unit, var flag: Boolean) {}

class LinkerAPI {
    var state = "default";
    var onStateHandlers: ArrayList<OnStateHandler> = arrayListOf();
    fun setState_(state: String) {
        this.state = state;
        for ( i in onStateHandlers.indices ) {
            if ( state == onStateHandlers[i].stateName ) {
                onStateHandlers[i].func(state);
            }
        }
    }

    var voidHandlers: ArrayList<VoidHandler> = arrayListOf();
    var doubleHandlers: ArrayList<DoubleHandler> = arrayListOf();
    var arrayDoubleHandlers: ArrayList<ArrayDoubleHandler> = arrayListOf();
    var timersHandlers: ArrayList<TimerHandler> = arrayListOf();
    var flaggedButtonHandlers: ArrayList<FlaggedButtonHandler> = arrayListOf();

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
        for ( i in timersHandlers.indices ) {
            if ( time - timersHandlers[i].timeout > timersHandlers[i].timer ) {
                timersHandlers[i].func();
                timersHandlers.removeAt(i);
                break;
            }
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

    var modules: ArrayList<Module> = arrayListOf();

    fun addModules(modulesToAdd: ArrayList<Module>) {
        modules += modulesToAdd;
    }

    fun <T: Module>requestModule(module: KClass<T>): Module? {
        for ( m in modules ) {
            if ( m::class == module ) { return m; }
        }
        return null;
    }

}