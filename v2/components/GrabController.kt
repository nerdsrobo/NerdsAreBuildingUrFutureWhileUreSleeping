package org.firstinspires.ftc.teamcode.v2.components

import org.firstinspires.ftc.teamcode.v2.components.superclasses.ProgramComponent
import org.firstinspires.ftc.teamcode.v2.components.util.ArtefactColor
import org.firstinspires.ftc.teamcode.v2.components.util.Calculations
import org.firstinspires.ftc.teamcode.v2.modules.CameraOnGrab
import org.firstinspires.ftc.teamcode.v2.modules.Grab
import org.firstinspires.ftc.teamcode.v2.modules.superclasses.RobotPack
import org.firstinspires.ftc.teamcode.v2.util.dashconfigs.ConfigGrabCam
import org.firstinspires.ftc.teamcode.v2.util.dashconfigs.ConfigGrabController
import org.firstinspires.ftc.teamcode.v2.util.dashconfigs.ConfigGrabController.green_hl
import org.firstinspires.ftc.teamcode.v2.util.dashconfigs.ConfigGrabController.green_sl
import org.firstinspires.ftc.teamcode.v2.util.dashconfigs.ConfigGrabController.green_vl
import org.firstinspires.ftc.teamcode.v2.util.dashconfigs.ConfigGrabController.green_hh
import org.firstinspires.ftc.teamcode.v2.util.dashconfigs.ConfigGrabController.green_sh
import org.firstinspires.ftc.teamcode.v2.util.dashconfigs.ConfigGrabController.green_vh
import org.firstinspires.ftc.teamcode.v2.util.dashconfigs.ConfigGrabController.purple_hl
import org.firstinspires.ftc.teamcode.v2.util.dashconfigs.ConfigGrabController.purple_sl
import org.firstinspires.ftc.teamcode.v2.util.dashconfigs.ConfigGrabController.purple_vl
import org.firstinspires.ftc.teamcode.v2.util.dashconfigs.ConfigGrabController.purple_hh
import org.firstinspires.ftc.teamcode.v2.util.dashconfigs.ConfigGrabController.purple_sh
import org.firstinspires.ftc.teamcode.v2.util.dashconfigs.ConfigGrabController.purple_vh
import org.opencv.core.Core
import org.opencv.core.Mat
import org.opencv.core.MatOfPoint
import org.opencv.core.Point
import org.opencv.core.Rect
import org.opencv.core.Scalar
import org.opencv.imgproc.Imgproc
import org.openftc.easyopencv.OpenCvPipeline
import kotlin.math.abs

class ColorDetector {
    companion object {
        private val hsv = Mat()
        private val maskGreen = Mat()
        private val maskPurple = Mat()
        private val contours = ArrayList<MatOfPoint>()
        private val hierarchy = Mat()

        fun detect(input: Mat): ArrayList<ArtefactBox> {
            Imgproc.cvtColor(input, hsv, Imgproc.COLOR_BGR2HSV)

            Core.inRange(hsv, Scalar(green_hl, green_sl, green_sl), Scalar(green_hh, green_sh, green_vh), maskGreen)
            Core.inRange(hsv, Scalar(purple_hl, purple_sl, purple_vl), Scalar(purple_hh, purple_sh, purple_vh), maskPurple)

            val result = ArrayList<ArtefactBox>()
            result.addAll(findBoxes(maskGreen, ArtefactColor.GREEN))
            result.addAll(findBoxes(maskPurple, ArtefactColor.PURPLE))
            return result
        }

        private fun findBoxes(mask: Mat, color: ArtefactColor): ArrayList<ArtefactBox> {
            contours.clear()
            Imgproc.findContours(mask, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE)
            val boxes = ArrayList<ArtefactBox>()
            for (c in contours) {
                if (Imgproc.contourArea(c) < ConfigGrabController.minArea) continue
                val r: Rect = Imgproc.boundingRect(c)
                boxes.add(ArtefactBox(r.x.toDouble(), r.y.toDouble(), (r.x + r.width).toDouble(), (r.y + r.height).toDouble(), color))
            }
            return boxes
        }
    }
}

class ArtefactBox(val x1: Double, val y1: Double, val x2: Double, val y2: Double, val color: ArtefactColor) {}

class GrabControllerCamPipeline : OpenCvPipeline() {
    var detections: ArrayList<ArtefactBox> = arrayListOf()
    override fun processFrame(input: Mat?): Mat? {
        detections = if (input != null) ColorDetector.detect(input) else arrayListOf()
        return input
    }
}

class GrabControllerCamPipelineTest : OpenCvPipeline() {
    override fun processFrame(input: Mat?): Mat? {
        if (input != null) {
            for (d in ColorDetector.detect(input)) {
                Imgproc.rectangle(input, Point(d.x1, d.y1), Point(d.x2, d.y2), if (d.color == ArtefactColor.GREEN) Scalar(0.0, 255.0, 0.0) else Scalar(255.0, 0.0, 255.0), 3)
            }
        }
        return input
    }
}

class ArtefactPlacerPic2r() {
    class DistXYColor(val y: Double, val x: Double, val color: ArtefactColor) {}
    companion object {
        fun calcDists(artefactBoxes: ArrayList<ArtefactBox>): ArrayList<DistXYColor> {
            val output: ArrayList<DistXYColor> = arrayListOf();
            for ( art in artefactBoxes ) {
                val dists = Calculations.pic2r(
                    ConfigGrabCam.alpha, ConfigGrabCam.beta,
                    art.x1, art.y2,
                    ConfigGrabCam.h, ConfigGrabCam.fx, ConfigGrabCam.fy, ConfigGrabCam.cx, ConfigGrabCam.cy
                )
                output.add(DistXYColor(dists.y+ConfigGrabCam.offsetY, dists.x+ConfigGrabCam.offsetX, art.color));
            }
            return output;
        }
    }
}

class GrabController(P: RobotPack): ProgramComponent(P) {

    private val grb = requestModule(Grab::class) as Grab;
    private val camGrb = requestModule(CameraOnGrab::class) as CameraOnGrab;
    private val pipe = GrabControllerCamPipeline()
    private val pipeTest = GrabControllerCamPipelineTest()

    var artefacts: ArrayList<ArtefactPlacerPic2r.DistXYColor> = arrayListOf();

    var readColor = true;
    var snapped = false;
    var lastSnappedColor = ArtefactColor.PURPLE;
    var automatic = false;

    fun open(isStreamToDash: Boolean) {
        camGrb.openWithPipeline(pipe, isStreamToDash);
    }
    fun openTest() {
        camGrb.openWithPipeline(pipeTest, true);
    }

    fun close() {
        camGrb.close();
    }

    fun updateArtefactsPlaces() {
        artefacts = ArtefactPlacerPic2r.calcDists(pipe.detections);
    }

    fun swallow(): ArtefactColor {
        snapped = false;
        return lastSnappedColor;
    }

    fun take() { grb.setGrbPower(1.0);  }
    fun stop() { grb.setGrbPower(0.0);  }
    fun spit() { grb.setGrbPower(-1.0); }

    override fun tick() {
        if ( (readColor || automatic) && camGrb.isOpened ) {
            updateArtefactsPlaces();
            if ( readColor && !snapped ) {
                for ( art in artefacts ) {
                    if ( abs(art.y) <= ConfigGrabController.distYToSnap && abs(art.x) <= ConfigGrabController.distXToSnap ) {
                        lastSnappedColor = art.color;
                        snapped = true;
                        break;
                    }
                }
            }
        }
    }

}