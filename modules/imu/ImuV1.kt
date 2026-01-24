package org.firstinspires.ftc.teamcode.modules.imu

import com.qualcomm.hardware.bosch.BNO055IMU
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator
import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference
import org.firstinspires.ftc.robotcore.external.navigation.Orientation
import org.firstinspires.ftc.teamcode.modules.superclasses.Module
import org.firstinspires.ftc.teamcode.modules.superclasses.RobotPack


class ImuV1(P: RobotPack) : Module(P) {
    var imu: BNO055IMU = P.hwmp.get(BNO055IMU::class.java, "imu");
    init {
        val parameters = BNO055IMU.Parameters() // инициализация Акселерометра
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"
        parameters.loggingEnabled = true
        parameters.loggingTag = "IMU"
        parameters.accelerationIntegrationAlgorithm = JustLoggingAccelerationIntegrator()
        imu = P.hwmp.get(BNO055IMU::class.java, "imu")
        imu.initialize(parameters)
        while (!imu.isGyroCalibrated) { //Калибровка акселерометра
            Thread.sleep(50);
            P.telemetry.addData("Wait", "Calibration") //Сообщение о калибровке
            P.telemetry.update()
        }
        P.telemetry.addData("Done!", "Calibrated") //Сообщение об окончании калибровки
        P.telemetry.update()
    }

    fun getAngle(): Float {
        //Функция получения данных с акселерометра
        val angles: Orientation =
            imu.getAngularOrientation(
                AxesReference.INTRINSIC,
                AxesOrder.ZYX,
                AngleUnit.DEGREES
            ) // переменная в которой будет храниться угол поворота под акселерометр
        val gravity = imu.gravity // здесь хранится важная информация для акселерометра
        return angles.firstAngle
    }
}