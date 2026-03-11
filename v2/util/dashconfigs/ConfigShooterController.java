package org.firstinspires.ftc.teamcode.v2.util.dashconfigs;

import com.acmerobotics.dashboard.config.Config;

@Config
public class ConfigShooterController {
    public static double kp = .001;
    public static double ki = .00001;
    public static double kd = .005;
    public static double revolutionsThr = .5;
    public static double servoDownPos = .05;
    public static double servoUpPos = .95;
    public static double servoDownAngle = 30;
    public static double servoUpAngle = 70;
    public static double nearMotorW = 6.0;
    public static double farMotorW = 9.5;
}
