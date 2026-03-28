package org.firstinspires.ftc.teamcode.v2.components.util

import org.opencv.core.*
import org.opencv.imgproc.Imgproc
import org.openftc.easyopencv.OpenCvPipeline
import java.util.LinkedList

class GrabColorPipeline : OpenCvPipeline() {

    enum class ArtefactColor { PURPLE, GREEN, NONE }

    val colorQueue: LinkedList<ArtefactColor> = LinkedList()

    @Volatile
    var triggerCapture = false

    @Volatile
    var detectedColor: ArtefactColor = ArtefactColor.NONE
    @Volatile
    var detectedArea: Double = 0.0


    private val purpleLow = Scalar(125.0, 60.0, 60.0)
    private val purpleHigh = Scalar(155.0, 255.0, 255.0)

    private val greenLow = Scalar(40.0, 60.0, 60.0)
    private val greenHigh = Scalar(85.0, 255.0, 255.0)

    private val hsv = Mat()
    private val maskP = Mat()
    private val maskG = Mat()
    private val kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, Size(5.0, 5.0))

    override fun processFrame(input: Mat): Mat {
        Imgproc.cvtColor(input, hsv, Imgproc.COLOR_RGB2HSV)

        Core.inRange(hsv, purpleLow, purpleHigh, maskP)
        Core.inRange(hsv, greenLow, greenHigh, maskG)

        Imgproc.morphologyEx(maskP, maskP, Imgproc.MORPH_OPEN, kernel)
        Imgproc.morphologyEx(maskG, maskG, Imgproc.MORPH_OPEN, kernel)

        val areaP = maxContourArea(maskP)
        val areaG = maxContourArea(maskG)

        detectedColor = when {
            areaP == 0.0 && areaG == 0.0 -> ArtefactColor.NONE
            areaP >= areaG -> ArtefactColor.PURPLE
            else -> ArtefactColor.GREEN
        }
        detectedArea = maxOf(areaP, areaG)

        if (triggerCapture) {
            if (detectedColor != ArtefactColor.NONE) {
                colorQueue.add(detectedColor)
            }
            triggerCapture = false
        }

        drawContours(input, maskP, Scalar(180.0, 0.0, 255.0))
        drawContours(input, maskG, Scalar(0.0, 255.0, 0.0))

        return input
    }

    private fun maxContourArea(mask: Mat): Double {
        val contours = mutableListOf<MatOfPoint>()
        Imgproc.findContours(
            mask,
            contours,
            Mat(),
            Imgproc.RETR_EXTERNAL,
            Imgproc.CHAIN_APPROX_SIMPLE
        )
        return contours.maxOfOrNull { Imgproc.contourArea(it) } ?: 0.0
    }

    private fun drawContours(dst: Mat, mask: Mat, color: Scalar) {
        val contours = mutableListOf<MatOfPoint>()
        Imgproc.findContours(
            mask,
            contours,
            Mat(),
            Imgproc.RETR_EXTERNAL,
            Imgproc.CHAIN_APPROX_SIMPLE
        )
        Imgproc.drawContours(dst, contours, -1, color, 2)
    }
}