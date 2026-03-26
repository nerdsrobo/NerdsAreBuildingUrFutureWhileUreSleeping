package org.firstinspires.ftc.teamcode.v2.components.util

import org.firstinspires.ftc.teamcode.v2.util.dashconfigs.ConfigLookAtGoalCalcs
import org.firstinspires.ftc.teamcode.v2.util.dashconfigs.ConfigOdometry
import org.firstinspires.ftc.teamcode.v2.util.dashconfigs.ConfigShooterController
import kotlin.math.PI
import kotlin.math.atan
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sign
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.math.tan

class Calculations {
    class Coords(val x: Double, val y: Double) {}
    companion object {
        fun pic2r(alpha: Double, beta: Double, x: Double, y: Double, h: Double, fx: Double, fy: Double, cx: Double, cy: Double): Coords {
            /*
                Подсчет расстояния в см по двум осям до точки на земле, получаемую из точки соприкасновения воображаемого луча,
                пущенного из оптического центра камеры в пиксель (x, y) и земли.
                alpha - угол поворота камеры
                beta - угол наклона камеры
                x - координата пикселя x
                y - координата пикселя y
                h - высота оптического центра камеры
            */
            val fx: Double = fx
            val fy: Double = fy
            val cx: Double = cx
            val cy: Double = cy
            val a = alpha * Math.PI / 180;
            val b = beta * Math.PI / 180;


            val y_s_shapochkoy = h * (1 / tan(atan((y - cy) / fy) + b))
            val x_s_shapochkoy = (y_s_shapochkoy * (x - cx)) / fx

            val x_x0 = y_s_shapochkoy * sin(a)
            val y_x0 = y_s_shapochkoy * cos(a)

            val x_y0 = x_s_shapochkoy * cos(a)
            val y_y0 = x_s_shapochkoy * -sin(a)

            return Coords(x_x0 + x_y0, y_x0 + y_y0)
        }// да, взял у себя прошлого
        fun rotateCoordSystem(x: Double, y: Double, alpha: Double): Coords {
            val a = alpha * Math.PI / 180;

            val x_x0 = y * sin(a);
            val y_x0 = y * cos(a);

            val x_y0 = x * cos(a);
            val y_y0 = x * -sin(a);

            return Coords(x_x0 + x_y0, y_x0 + y_y0);
        }
        class RotationCalculations(val revs: Double, val Ro: Double) {}
        fun calcRotationByOdometry(phiYA: Int, phiYB: Int): RotationCalculations {
            val a = phiYA * ConfigOdometry.ticksToRev * ConfigOdometry.deadWheelR;
            val b = phiYB * ConfigOdometry.ticksToRev * ConfigOdometry.deadWheelR;
            if ( a == b ) { return RotationCalculations(.0, .0); }
            val c = ConfigOdometry.offsetYAx;
            val d = ConfigOdometry.offsetYAy;
            val e = ConfigOdometry.offsetYBx;
            val f = ConfigOdometry.offsetYBy;
            val Ro = (a.pow(2)*c - e*a.pow(2) + sqrt(-a.pow(4) *f.pow(2) + a.pow(2)*b.pow(2)*c.pow(2) - 2*e*a.pow(2)*b.pow(2)*c + a.pow(2)*b.pow(2)*d.pow(2) + a.pow(2)*b.pow(2)*f.pow(2) + a.pow(2)*b.pow(2)*e.pow(2) - b.pow(4)*d.pow(2)))/(a.pow(2) - b.pow(2))
            return RotationCalculations((a)/sqrt((Ro + c).pow(2) + d.pow(2))*sign(a-b), Ro);
        }
        fun pointsAngle(point1: Coords, point2: Coords): Double {
            val deltX = point2.x - point1.x;
            val deltY = point2.y - point1.y;
            if  ( deltY == .0 ) {
                if ( deltX > 0 ) { return 90.0; }
                return -90.0;
            }
            var angle = atan(deltX / deltY) / PI * 180;
            if ( deltY < 0 ) { angle += if ( angle < 0 ) 180 else -180; }
            return angle;
        }
        fun calcParabolaX(alpha: Double, V: Double, g: Double, y: Double, is1parabolaPoint: Boolean): Double {
            val a = alpha * PI / 180;
            val s = if ( is1parabolaPoint ) -sqrt((sin(a)*V).pow(2) - 2*g*y) else sqrt((sin(a)*V).pow(2) - 2*g*y)
            return (cos(a)*V*(sin(a) * V + s)) / g;
        }
        fun shootingTraectoryCalc(robotPoint: Coords, targetPoint: Coords, targetPointHeight: Double, wheelW: Double, is1parabolaPoint: Boolean): Double {
            val V = wheelW * ConfigLookAtGoalCalcs.shooterWheelD * PI;
            val distToGoal = sqrt((robotPoint.x-targetPoint.x).pow(2) + (robotPoint.y-targetPoint.y).pow(2))
            var alpha = ConfigShooterController.servoDownAngle;
            while ( alpha < ConfigShooterController.servoUpAngle+.1 ) {
                if ( calcParabolaX(alpha, V, 9.80665, targetPointHeight, is1parabolaPoint) > distToGoal ) { break; }
                alpha += .5;
            }
            return alpha;
        }
    }
}