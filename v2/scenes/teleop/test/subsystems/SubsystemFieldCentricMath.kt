package org.firstinspires.ftc.teamcode.v2.scenes.teleop.test.subsystems

import com.acmerobotics.dashboard.FtcDashboard
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.v2.components.AngleSponsor
import org.firstinspires.ftc.teamcode.v2.components.AprilTagRuler
import org.firstinspires.ftc.teamcode.v2.modules.CameraOnShooter
import org.firstinspires.ftc.teamcode.v2.modules.Imu
import org.firstinspires.ftc.teamcode.v2.modules.Wheelbase
import org.firstinspires.ftc.teamcode.v2.modules.superclasses.RobotPack
import org.firstinspires.ftc.teamcode.v2.scenes.superclasses.TeleOpPacker
import org.firstinspires.ftc.teamcode.v2.systems.HogRider
import kotlin.math.abs

@TeleOp(name = "SUBSYSTEM - Field-Centric Math", group = "subsystem")
class SubsystemFieldCentricMath(): TeleOpPacker() {
    override fun init_(P: RobotPack) {
        val dashTelemetry = FtcDashboard.getInstance().telemetry;

        val wb = Wheelbase(P);
        val camOnSht = CameraOnShooter(P);
        val aprilTagRuler = AprilTagRuler(P); // он не используется, но нужен хог райдеру
        val imu = Imu(P);
        val ass = AngleSponsor(P);
        linkerApi.alwaysActiveComponents.add(ass);

        var blockMoving = false;

        val hogRider = HogRider(P);

        hogRider.updateByImu = true;
        hogRider.controlAngle = true;

        linkerApi.bindArray({blockMoving}, {true},
            {arrayOf(gamepad1.left_stick_x.toDouble(), gamepad1.left_stick_y.toDouble())},
            {pws -> run{hogRider.pwX = pws[0]; hogRider.pwY = pws[1]; hogRider.controlXY = false;}});
        var rotateFlag = false;
        linkerApi.bindDouble({blockMoving}, {true}, {gamepad1.right_stick_x.toDouble()}, {pw -> run{
            if ( abs(pw) > .08 ) { if ( !rotateFlag ) { rotateFlag = true; }; hogRider.pwROverride = pw; hogRider.controlAngle = false; }
            else { if (rotateFlag) { rotateFlag = false; hogRider.targetAngle = hogRider.angle; hogRider.controlAngle = true; } }
        }})

        linkerApi.bindVoid({true}, {gamepad1.a}, {
            ass.angleOffset = (-imu.getAngle()).toDouble();
        })
        linkerApi.bindFlag({!blockMoving}, {gamepad1.y}, {
            blockMoving = true;
            hogRider.targetAngle = 135.0;
            hogRider.controlAngle = true;
        });
        linkerApi.bindFlag({blockMoving}, {gamepad1.b}, {
            blockMoving = false;
            hogRider.targetAngle = ass.getCurrAngle();
        })
        linkerApi.bindVoid({true}, {true}, {
            dashTelemetry.addData("imu real", imu.getAngle());
            dashTelemetry.addData("curr ass", ass.getCurrAngle());
            dashTelemetry.addData("angle offset", ass.angleOffset);
            dashTelemetry.addData("rotateFlag", rotateFlag);
            dashTelemetry.addData("blockMoving", blockMoving);
            dashTelemetry.addData("pwX", hogRider.pwX);
            dashTelemetry.addData("pwY", hogRider.pwY);
            dashTelemetry.addData("pwR", hogRider.pwY);
            dashTelemetry.update();
        })
    }
}