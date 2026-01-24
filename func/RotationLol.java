package org.firstinspires.ftc.teamcode.func;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.modules.imu.ImuImuv1;
import org.firstinspires.ftc.teamcode.modules.imu.ImuV1;
import org.firstinspires.ftc.teamcode.modules.wheelbase.versions.WheelbaseMecanumV1;

@Config
public class RotationLol {
    public static double kp = -.01;
    public static double kd = -.1;
    public static double ErTarget = .5;
    public static double ErSpeedTarget = .5;
    double ErLast = 0;
    ImuImuv1 imu;
    WheelbaseMecanumV1 wb;
    LinearOpMode L;
    Telemetry telemetry;

    public RotationLol(ImuImuv1 imu, WheelbaseMecanumV1 wb, LinearOpMode L, Telemetry telemetry) {
        this.imu = imu;
        this.wb = wb;
        this.L = L;
        this.telemetry = telemetry;
    }

    public void rot(double angle) {
        ErLast = 0;
        while ( (Math.abs(angle - imu.getAngle()) > ErTarget || Math.abs((angle - imu.getAngle()) - ErLast) > ErSpeedTarget) && L.opModeIsActive() ) {
            double Er = angle - imu.getAngle();
            double P = Er * kp;
            double D = (Er - ErLast) * kd;
            ErLast = Er;
            double U = P+D;
            wb.setMtPower(U, U, U, U);
            telemetry.addData("U", U);
            telemetry.addData("P", P);
            telemetry.addData("D", D);
            telemetry.addData("angle", imu.getAngle());
            telemetry.addData("Er", angle-imu.getAngle());
            telemetry.update();

        }
        wb.setAxisPower(0, 0, 0);
    }
}
