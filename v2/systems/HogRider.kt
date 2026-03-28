package org.firstinspires.ftc.teamcode.v2.systems

import org.firstinspires.ftc.teamcode.util.Utils
import org.firstinspires.ftc.teamcode.v2.components.AngleSponsor
import org.firstinspires.ftc.teamcode.v2.components.AprilTagRuler
import org.firstinspires.ftc.teamcode.v2.components.Odometry
import org.firstinspires.ftc.teamcode.v2.components.superclasses.PID
import org.firstinspires.ftc.teamcode.v2.components.util.Alliance
import org.firstinspires.ftc.teamcode.v2.components.util.Calculations
import org.firstinspires.ftc.teamcode.v2.components.util.UsefulFuncs
import org.firstinspires.ftc.teamcode.v2.modules.Wheelbase
import org.firstinspires.ftc.teamcode.v2.modules.superclasses.RobotPack
import org.firstinspires.ftc.teamcode.v2.systems.superclasses.ProgramSystem
import org.firstinspires.ftc.teamcode.v2.util.dashconfigs.ConfigHogRider

class HogRider(P: RobotPack): ProgramSystem(P) {
    private val wb = requestModule(Wheelbase::class) as Wheelbase;
    //private val odometry = requestComponent(Odometry::class) as Odometry;
    private val aprilTagRuler = requestComponent(AprilTagRuler::class) as AprilTagRuler;
    private val angleSponsor = requestComponent(AngleSponsor::class) as AngleSponsor;

    var startAbsX = 30.0;
    var startAbsY = 30.0;

    var absX = 30.0;
    var absY = 30.0;
    var angle = 0.0;

    var updateByImu = false;
    var calcOdometryPos = false;
    var calcAprilTagPos = false;
    var aprilTagFound = false;

    var targetX = 30.0;
    var targetY = 30.0;
    var targetAngle = 0.0;
    var controlXY = false;
    var controlAngle = false;

    var pwX = .0;
    var pwY = .0;
    var pwR = .0;
    var rotateCoords = true;
    var normPower = true;
    var powerAxis = true;

    var maxNormPw = 1.0;

    var pwROverride = .0;

    val moveXPID = PID(ConfigHogRider.moveXY_kp, ConfigHogRider.moveXY_ki, ConfigHogRider.moveXY_kd);
    val moveYPID = PID(ConfigHogRider.moveXY_kp, ConfigHogRider.moveXY_ki, ConfigHogRider.moveXY_kd);
    val moveRPID = PID(ConfigHogRider.moveR_kp, ConfigHogRider.moveR_ki, ConfigHogRider.moveR_kd);

    fun setupAngle(startAngle: Double) {
        angle = startAngle;
        targetAngle = startAngle;
        angleSponsor.angleOffset = -startAngle;
    }

    fun setXYPos(targetX: Double, targetY: Double) {
        moveXPID.reset(); moveYPID.reset();
        this.targetX = targetX; this.targetY = targetY;
        controlXY = true;
    }

    fun setRotation(targetAngle: Double) {
        moveRPID.reset();
        this.targetAngle = targetAngle;
        controlAngle = true;
    }

    fun setAxisPower(pwX: Double, pwY: Double, pwR: Double) {
        controlXY = false;
        controlAngle = false;
        this.pwX = pwX; this.pwY = pwY; this.pwR = pwR;
    }

    private fun setAxisPowerTick() {
        var npwX = pwX;
        var npwY = pwY;
        var npwR = pwR;
        if ( rotateCoords ) {
            val rotated = Calculations.rotateCoordSystem(npwX, npwY, -angle);
            npwX = rotated.x;
            npwY = rotated.y;
        }
        if ( normPower ) {
            val normed = UsefulFuncs.normToMax(arrayListOf(npwX, npwY, npwR), maxNormPw);
            npwX = normed[0]; npwY = normed[1]; npwR = normed[2];
        }
        wb.setAxisPower(npwX, npwY, npwR);
    }

    override fun tick() {
        //if ( calcOdometryPos ) {
        //    absX = startAbsX + odometry.relX;
        //    absY = startAbsY + odometry.relY;
        //}
        if ( calcAprilTagPos ) {
            absX = aprilTagRuler.absX;
            absY = aprilTagRuler.absY;
            aprilTagFound = aprilTagRuler.absUpdated;
        }
        if ( updateByImu ) { angle = angleSponsor.getCurrAngle(); }
        else { if (calcAprilTagPos && aprilTagRuler.absUpdated) { angle = aprilTagRuler.lastAngle; } /*else { angle = odometry.lastAngle; }*/ }
        if ( controlXY ) {
            pwX = moveXPID.tick(targetX - absX);
            pwY = moveYPID.tick(targetY - absY);
        }
        if ( controlAngle ) {

            //попытка в исправление ошибки 180
            pwR = moveRPID.tick( - UsefulFuncs.constrTo180(angle - targetAngle) );

            //pwR = moveRPID.tick(targetAngle - angle);
        } else { pwR = pwROverride; }
        if ( powerAxis ) { setAxisPowerTick(); }
    }
}