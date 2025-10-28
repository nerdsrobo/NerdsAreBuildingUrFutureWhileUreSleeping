package org.firstinspires.ftc.teamcode;

open class GamepadAPI {
    var state = "default";
    var onStateHandlersStates: ArrayList<String> = arrayListOf();
    var onStateHandlersFuncs: ArrayList<() -> Unit> = arrayListOf();
    fun setState(state: String) {
        this.state = state;
        for ( i in onStateHandlersStates.indices ) {
            if ( state == onStateHandlersStates[i] ) {
                onStateHandlersFuncs[i]();
            }
        }
    }
    var voidLambdaHandlersCondition: ArrayList<() -> Boolean> = arrayListOf();
    var voidLambdaHandlersConditionButton: ArrayList<() -> Boolean> = arrayListOf();
    var voidLambdaHandlersFuncs: ArrayList<() -> Unit> = arrayListOf();
    var doubleLambdaHandlersCondition: ArrayList<() -> Boolean> = arrayListOf();
    var doubleLambdaHandlersConditionButton: ArrayList<() -> Boolean> = arrayListOf();
    var doubleLambdaHandlersDoubleGetter: ArrayList<() -> Double> = arrayListOf();
    var doubleLambdaHandlersFuncs: ArrayList<(Double) -> Unit> = arrayListOf();
    var arrayDoubleLambdaHandlersCondition: ArrayList<() -> Boolean> = arrayListOf();
    var arrayDoubleLambdaHandlersConditionButton: ArrayList<() -> Boolean> = arrayListOf();
    var arrayDoubleLambdaHandlersArrayGetter: ArrayList<() -> Array<Double>> = arrayListOf();
    var arrayDoubleLambdaHandlersFuncs: ArrayList<(Array<Double>) -> Unit> = arrayListOf();
    fun tick() {
        for ( i in voidLambdaHandlersCondition.indices ) {
            if ( voidLambdaHandlersCondition[i]() && voidLambdaHandlersConditionButton[i]() ) {
                voidLambdaHandlersFuncs[i]();
            }
        }
        for ( i in doubleLambdaHandlersCondition.indices ) {
            if ( doubleLambdaHandlersCondition[i]() && doubleLambdaHandlersConditionButton[i]() ) {
                doubleLambdaHandlersFuncs[i](doubleLambdaHandlersDoubleGetter[i]());
            }
        }
        for ( i in arrayDoubleLambdaHandlersCondition.indices ) {
            if ( arrayDoubleLambdaHandlersCondition[i]() && arrayDoubleLambdaHandlersConditionButton[i]() ) {
                arrayDoubleLambdaHandlersFuncs[i](arrayDoubleLambdaHandlersArrayGetter[i]());
            }
        }
    }
    fun bind(condition: () -> Boolean, button: () -> Boolean, func: () -> Unit) {
        voidLambdaHandlersCondition.add(condition);
        voidLambdaHandlersConditionButton.add(button);
        voidLambdaHandlersFuncs.add(func);
    }
    fun bind(condition: () -> Boolean, button: () -> Boolean, doubleGetter: () -> Double, func: (Double) -> Unit) {
        doubleLambdaHandlersCondition.add(condition);
        doubleLambdaHandlersConditionButton.add(button);
        doubleLambdaHandlersDoubleGetter.add(doubleGetter);
        doubleLambdaHandlersFuncs.add(func);
    }
    fun bind(condition: () -> Boolean, button: () -> Boolean, arrayGetter: () -> Array<Double>, func: (Array<Double>) -> Unit) {
        arrayDoubleLambdaHandlersCondition.add(condition);
        arrayDoubleLambdaHandlersConditionButton.add(button);
        arrayDoubleLambdaHandlersArrayGetter.add(arrayGetter);
        arrayDoubleLambdaHandlersFuncs.add(func);
    }
    fun onState(state: String, func: () -> Unit) {
        onStateHandlersStates.add(state);
        onStateHandlersFuncs.add(func);
    }
}