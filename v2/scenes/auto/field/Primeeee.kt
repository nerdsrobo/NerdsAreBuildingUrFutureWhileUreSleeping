package org.firstinspires.ftc.teamcode.v2.scenes.auto.field

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import org.firstinspires.ftc.teamcode.v2.components.AngleSponsor
import org.firstinspires.ftc.teamcode.v2.components.AprilTagRuler
import org.firstinspires.ftc.teamcode.v2.components.GrabController
import org.firstinspires.ftc.teamcode.v2.components.ShooterV2Controller
import org.firstinspires.ftc.teamcode.v2.components.util.Alliance
import org.firstinspires.ftc.teamcode.v2.modules.CameraOnGrab
import org.firstinspires.ftc.teamcode.v2.modules.CameraOnShooter
import org.firstinspires.ftc.teamcode.v2.modules.Grab
import org.firstinspires.ftc.teamcode.v2.modules.Imu
import org.firstinspires.ftc.teamcode.v2.modules.Shooter
import org.firstinspires.ftc.teamcode.v2.modules.ShooterV2
import org.firstinspires.ftc.teamcode.v2.modules.TolkalkaV2
import org.firstinspires.ftc.teamcode.v2.modules.Wheelbase
import org.firstinspires.ftc.teamcode.v2.modules.superclasses.RobotPack
import org.firstinspires.ftc.teamcode.v2.scenes.superclasses.AutonomousPacker

@Autonomous(name = "primeee - red")
class PrimeeeRed(): Primeeee(Alliance.RED) {}

@Autonomous
class PrimeeeBlue(): Primeeee(Alliance.BLUE) {}

open class Primeeee(alliance: Alliance) : AutonomousPacker(alliance) {
    val wb: Wheelbase = Wheelbase(P);
    val imu: Imu = Imu(P);
    val grb: Grab = Grab(P);
    val sht: ShooterV2 = ShooterV2(P);
    val tlk: TolkalkaV2 = TolkalkaV2(P);
    val camGrb: CameraOnGrab = CameraOnGrab(P);
    val camSht: CameraOnShooter = CameraOnShooter(P);

    val ass = AngleSponsor(P);
    val aprilTagRule = AprilTagRuler(P);
    val grbControl = GrabController(P);
    val shtControl = ShooterV2Controller(P);

    override fun init_(P: RobotPack) {

    }

    override fun moves(P: RobotPack) {
        TODO("Not yet implemented")
    }
}