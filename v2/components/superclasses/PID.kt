package org.firstinspires.ftc.teamcode.v2.components.superclasses

class PID(val kp: Double, val ki: Double, val kd: Double) {
    var ErD = 0.0;
    var Ir = 0.0;

    fun reset() {
        ErD = 0.0;
        Ir = 0.0;
    }

    fun tick(Er: Double): Double {
        val P = Er * kp;
        Ir += Er;
        val I = Ir * ki;
        val D = ( Er - ErD ) * kd;
        ErD = Er;
        return P+I+D;
    }
}