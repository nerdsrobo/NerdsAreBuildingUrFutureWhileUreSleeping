package org.firstinspires.ftc.teamcode.modules.imu

import com.qualcomm.hardware.bosch.BHI260IMU
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot
import com.qualcomm.robotcore.hardware.IMU
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference
import org.firstinspires.ftc.teamcode.modules.superclasses.Module
import org.firstinspires.ftc.teamcode.modules.superclasses.RobotPack


class ImuV1(P: RobotPack) : Module(P) {
    var imu: BHI260IMU = P.hwmp.get(BHI260IMU ::class.java, "imu");
    init {
        imu = P.hwmp.get(BHI260IMU::class.java, "imu")

        imu.initialize(
            IMU.Parameters(
                RevHubOrientationOnRobot(
                    RevHubOrientationOnRobot.LogoFacingDirection.LEFT,
                    RevHubOrientationOnRobot.UsbFacingDirection.DOWN
                )
            )
        )
        imu.resetYaw()
    }

    fun getAngle(): Float {
        val orientation =
            imu.getRobotOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES)
        return -orientation.firstAngle
    }
}