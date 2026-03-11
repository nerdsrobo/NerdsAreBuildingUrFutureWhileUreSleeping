package org.firstinspires.ftc.teamcode.v2.components.superclasses

class PD(val kp: Double, val kd: Double) {
    var ErD = 0.0;

    fun reset() {
        ErD = 0.0;
    }

    fun tick(Er: Double): Double {
        val P = Er * kp;
        val D = ( Er - ErD ) * kd;
        ErD = Er;
        return P+D;
    }

}