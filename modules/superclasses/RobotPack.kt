package org.firstinspires.ftc.teamcode.modules.superclasses

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.hardware.Gamepad
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.robotcore.external.Telemetry

open class RobotPack(val LOP: LinearOpMode,
                     val hwmp: HardwareMap,
                     val telemetry: Telemetry,
                     val gamepad1: Gamepad,
                     val gamepad2: Gamepad) {

}