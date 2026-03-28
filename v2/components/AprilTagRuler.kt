package org.firstinspires.ftc.teamcode.v2.components

import org.firstinspires.ftc.teamcode.v2.components.superclasses.ProgramComponent
import org.firstinspires.ftc.teamcode.v2.components.util.Alliance
import org.firstinspires.ftc.teamcode.v2.components.util.Calculations
import org.firstinspires.ftc.teamcode.v2.components.util.UsefulFuncs
import org.firstinspires.ftc.teamcode.v2.modules.CameraOnShooter
import org.firstinspires.ftc.teamcode.v2.modules.superclasses.RobotPack
import org.firstinspires.ftc.teamcode.v2.util.dashconfigs.ConfigAprilTags
import org.firstinspires.ftc.teamcode.v2.util.dashconfigs.ConfigShooterCam
import org.opencv.core.Mat
import org.opencv.core.Point
import org.opencv.core.Scalar
import org.opencv.imgproc.Imgproc
import org.openftc.apriltag.AprilTagDetection
import org.openftc.apriltag.AprilTagDetectorJNI
import org.openftc.apriltag.AprilTagDetectorJNI.TagFamily
import org.openftc.easyopencv.OpenCvPipeline
import kotlin.math.PI
import kotlin.math.abs

class AprilTagDetectionPipeline(private val detectorPtr: Long, val isTest: Boolean): OpenCvPipeline() {
    var detections: ArrayList<AprilTagDetection> = arrayListOf()
    override fun processFrame(input: Mat?): Mat? {
        if (input != null) {
            Imgproc.cvtColor(input, input, Imgproc.COLOR_BGR2GRAY)
            detections = AprilTagDetectorJNI.runAprilTagDetectorSimple(
                detectorPtr, input, ConfigAprilTags.aprilTagSize,
                ConfigShooterCam.fx, ConfigShooterCam.fy, ConfigShooterCam.cx, ConfigShooterCam.cy
            )
            if (isTest) {
                for (d in detections) {
                    Imgproc.rectangle(input, Point(d.corners[2].x, d.corners[2].y), Point(d.corners[0].x, d.corners[0].y), Scalar(255.0, 0.0, 200.0), 3)
                    Imgproc.putText(input, d.id.toString(), Point(d.corners[2].x, d.corners[2].y - 30.0), Imgproc.FONT_HERSHEY_PLAIN, 25.0, Scalar(255.0, 0.0, 200.0), 3)
                }
            }
        } else {
            //detections = arrayListOf()
        }
        return input
    }
}

enum class Motif {
    PPG, PGP, GPP, NULL
}

class AprilTagRuler(P: RobotPack): ProgramComponent(P) {

    val camSht = requestModule(CameraOnShooter::class) as CameraOnShooter

    val detectorPtr = AprilTagDetectorJNI.createApriltagDetector(TagFamily.TAG_36h11.string,  3f, 3)
    val pipe = AprilTagDetectionPipeline(detectorPtr, false)
    val pipeTest = AprilTagDetectionPipeline(detectorPtr, true)
    var isTest = false;

    var motif = Motif.NULL
    var readMotif = true

    var absX = .0
    var absY = .0
    var lastAngle = .0
    var absUpdated = false

    var lastRelCoords = arrayListOf(.0, .0, .0)

    private var centerKp = 0.03
    private var centerKd = 0.008
    private var centerPrevError = 0.0

    var centerThresholdDeg = 2.0

    var centerPwR = 0.0
        private set

    var isCentered = false
        private set

    var tagVisible = false
        private set

    fun open(isStreamToDash: Boolean) {
        camSht.openWithPipeline(pipe, isStreamToDash)
        isTest = false
    }
    fun openTest() {
        camSht.openWithPipeline(pipeTest, true)
        isTest = true
    }
    fun close() {
        absUpdated = false
        isCentered = false
        tagVisible = false
        centerPwR = 0.0
        centerPrevError = 0.0
        pipe.detections = arrayListOf()
        camSht.close()
    }

    override fun tick() {
        if (camSht.isOpened) {
            var updatedTemp = false
            tagVisible = false

            val snapshot = if ( isTest ) pipeTest.detections else pipe.detections;
            if ( isTest ) { P.telemetry.addData("snap", snapshot); }

            for (d in snapshot) {
                P.telemetry.addData("123", d);
                if (d.id in 21..23 && readMotif) {
                    when (d.id) {
                        21 -> motif = Motif.PPG
                        22 -> motif = Motif.PGP
                        23 -> motif = Motif.GPP
                    }
                } else {
                    updatedTemp = true
                    tagVisible = true
                    when (d.id) {
                        20 -> {
                            val relCoords = Calculations.rotateCoordSystem(
                                d.pose.x,
                                d.pose.y,
                                -d.pose.R[0, 0].toDouble() / PI * 180.0 - 45.0
                            )
                            lastRelCoords = arrayListOf(d.pose.x, d.pose.y, d.pose.R[0, 0].toDouble())
                            absX = 330 - relCoords.x - ConfigShooterCam.offsetX
                            absY = 330 - relCoords.y - ConfigShooterCam.offsetY
                            when (P.alliance) {
                                Alliance.BLUE -> {
                                    lastAngle = UsefulFuncs.constrTo180(d.pose.R[0, 0].toDouble() / PI * 180.0 + 45.0)
                                }
                                Alliance.RED -> {
                                    lastAngle = UsefulFuncs.constrTo180(d.pose.R[0, 0].toDouble() / PI * 180.0 - 135.0)
                                }
                            }
                        }
                        24 -> {
                            val relCoords = Calculations.rotateCoordSystem(
                                d.pose.x,
                                d.pose.y,
                                d.pose.R[0, 0].toDouble() / PI * 180.0 + 45.0
                            )
                            lastRelCoords = arrayListOf(d.pose.x, d.pose.y, d.pose.R[0, 0].toDouble())
                            absX = 330 - relCoords.x + ConfigShooterCam.offsetX
                            absY = 30 + relCoords.y + ConfigShooterCam.offsetY
                            when (P.alliance) {
                                Alliance.RED -> {
                                    lastAngle = UsefulFuncs.constrTo180(d.pose.R[0, 0].toDouble() / PI * 180.0 + 45.0)
                                }
                                Alliance.BLUE -> {
                                    lastAngle = UsefulFuncs.constrTo180(d.pose.R[0, 0].toDouble() / PI * 180.0 - 135.0)
                                }
                            }
                        }
                    }
                    val error = lastAngle
                    val derivative = error - centerPrevError
                    centerPwR = centerKp * error + centerKd * derivative
                    centerPrevError = error
                    isCentered = abs(error) < centerThresholdDeg
                }
            }

            if (!tagVisible) {
                centerPwR = 0.0
                isCentered = false
                centerPrevError = 0.0
            }

            absUpdated = updatedTemp
        }
    }
}