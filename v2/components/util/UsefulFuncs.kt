package org.firstinspires.ftc.teamcode.v2.components.util

import org.firstinspires.ftc.teamcode.util.Utils
import kotlin.math.abs
import kotlin.math.sign

class UsefulFuncs {
    companion object {
        private val utils = Utils();
        fun delay(millis: Long, nanos: Int = 0) { utils.delay(millis, nanos); }
        fun absSign(valu: Double, absMax: Double): Double { return utils.absSign(valu, absMax); }
        fun normToMax(values: ArrayList<Double>, maxVal: Double): ArrayList<Double> {
            val valOut = arrayListOf<Double>()
            var valSum = .0;
            for ( v in values ) { valSum += abs(v); }
            for ( v in values ) { valOut.add((abs(v) * maxVal / valSum)*sign(v)); }
            return valOut;
        }
        fun constrTo180(value: Double): Double {
            if ( value > 180 ) { return value-360; }
            if ( value < -180 ) { return value+360; }
            return value
        }
        fun arduinoLikeMap(value: Double, minimum1: Double, maximum1: Double, minimum2: Double, maximum2: Double): Double {
            val zeroToOne = ( value - minimum1 ) / ( maximum1 - minimum1 );
            return ( ( maximum2 - minimum2 ) * zeroToOne ) + minimum2;
        }
    }
}