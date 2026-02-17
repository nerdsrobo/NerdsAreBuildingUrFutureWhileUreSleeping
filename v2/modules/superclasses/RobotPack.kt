package org.firstinspires.ftc.teamcode.v2.modules.superclasses

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.hardware.Gamepad
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.robotcore.external.Telemetry
import org.firstinspires.ftc.teamcode.v2.LinkerAPI

open class RobotPack(val LOP: LinearOpMode,
                     val hwmp: HardwareMap,
                     val telemetry: Telemetry,
                     val gamepad1: Gamepad,
                     val gamepad2: Gamepad,
                     val linkerApi: LinkerAPI
) {

}