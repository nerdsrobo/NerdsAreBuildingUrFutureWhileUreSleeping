package org.firstinspires.ftc.teamcode.v2.components

import org.firstinspires.ftc.teamcode.v2.components.superclasses.PID
import org.firstinspires.ftc.teamcode.v2.components.superclasses.ProgramComponent
import org.firstinspires.ftc.teamcode.v2.components.util.UsefulFuncs
import org.firstinspires.ftc.teamcode.v2.modules.ShooterV2
import org.firstinspires.ftc.teamcode.v2.modules.superclasses.RobotPack
import org.firstinspires.ftc.teamcode.v2.util.dashconfigs.ConfigShooterController
import kotlin.math.abs

class ShooterV2Controller(P: RobotPack): ProgramComponent(P) {

    val sht = requestModule(ShooterV2::class) as ShooterV2;

    var targetW = 0.0
        get() = field;
        set(value) { field=value; }
    var stop = true;

    var control = true;

    var vel = 0.0;
    var velTs: Long = System.currentTimeMillis();
    var lastTicks: Int = 0;
    var U = 0.0;

    val TICKSTOREV = 1.0/28;

    val pid = PID(ConfigShooterController.kp, ConfigShooterController.ki, ConfigShooterController.kd);

    fun isOnRevolutions(): Boolean {
        return abs( targetW - vel ) < ConfigShooterController.revolutionsThr;
    }

    fun setAngle(angle: Double) {
        sht.setAnglePos(
            UsefulFuncs.arduinoLikeMap(angle, ConfigShooterController.servoDownAngle, ConfigShooterController.servoUpAngle, ConfigShooterController.servoDownPosL, ConfigShooterController.servoUpPosL),
            UsefulFuncs.arduinoLikeMap(angle, ConfigShooterController.servoDownAngle, ConfigShooterController.servoUpAngle, ConfigShooterController.servoDownPosR, ConfigShooterController.servoUpPosR))
    }

    override fun tick() {
        if ( control ) {
            if ( stop ) { sht.setShtPower(0.0); } else {
                val ticks = sht.getShtTicks();
                val timing = System.currentTimeMillis();
                val t = ( timing - velTs ).toDouble() / 1000.0;
                vel = ( ticks - lastTicks ) / t * TICKSTOREV;
                velTs = timing; lastTicks = ticks;
                U += pid.tick(targetW - vel) * t;
                sht.setShtPower(U);
            }
        }
    }
}