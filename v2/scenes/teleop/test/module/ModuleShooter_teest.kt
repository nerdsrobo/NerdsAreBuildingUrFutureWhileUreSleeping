package org.firstinspires.ftc.teamcode.v2.scenes.teleop.test.module

import com.acmerobotics.dashboard.FtcDashboard
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import org.firstinspires.ftc.teamcode.v2.modules.ShooterV2
import org.firstinspires.ftc.teamcode.v2.modules.superclasses.RobotPack
import org.firstinspires.ftc.teamcode.v2.scenes.superclasses.TeleOpPacker
import org.firstinspires.ftc.teamcode.v2.util.dashconfigs.ConfigShooterController
import org.firstinspires.ftc.teamcode.v2.util.dashconfigs.ConfigShooterV2
import org.firstinspires.ftc.teamcode.v2.util.dashconfigs.ConfigShooterV2.kSHT_L
import org.firstinspires.ftc.teamcode.v2.util.dashconfigs.ConfigShooterV2.kSHT_R
import org.firstinspires.ftc.teamcode.v2.util.dashconfigs.ConfigWheelbase

@TeleOp(name = "MODULE - SHOOTER_test_2", group = "module")
class ModuleShooter_teest() : TeleOpPacker() {
    override fun init_(P: RobotPack) {
        val Pdash = RobotPack(
            P.LOP,
            P.hwmp,
            FtcDashboard.getInstance().telemetry,
            gamepad1,
            gamepad2,
            linkerApi,
            alliance
        );
        val LB = P.hwmp.get(DcMotor::class.java, "SHT_L")
        val LBb = P.hwmp.get(DcMotor::class.java, "SHT_R")
        linkerApi.bindVoid({true}, {gamepad1.y}, {setPower(LB, LBb)});


    }
    fun setPower(LB:DcMotor, LBb:DcMotor) {

        if (gamepad1.a){
            LB.power = 1.0;
            LBb.power = 1.0;
        }

    }
}