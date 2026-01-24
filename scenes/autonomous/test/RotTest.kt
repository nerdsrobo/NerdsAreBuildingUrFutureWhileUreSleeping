package org.firstinspires.ftc.teamcode.scenes.autonomous.test

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import org.firstinspires.ftc.teamcode.modules.superclasses.RobotPack
import org.firstinspires.ftc.teamcode.modules.wheelbase.versions.WheelbaseMecanumV1
import org.firstinspires.ftc.teamcode.scenes.superclasses.AutonomousBuilder
import org.firstinspires.ftc.teamcode.func.RotationLol
import org.firstinspires.ftc.teamcode.modules.imu.ImuImuv1

@Autonomous(name = "Проиграли")
class RotTest() : AutonomousBuilder() {
    var wb: WheelbaseMecanumV1? = null;
    var imu: ImuImuv1? = null;
    var rotLol: RotationLol? = null;
    override fun init_(P: RobotPack) {
        wb = WheelbaseMecanumV1(P);
        imu = ImuImuv1(P.hwmp, P.telemetry);
        rotLol = RotationLol(imu, wb, this, telemetry);
    }

    override fun moves(P: RobotPack) {
        rotLol?.rot(45.0);
    }
}