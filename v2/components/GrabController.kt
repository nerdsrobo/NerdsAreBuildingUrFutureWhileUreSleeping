package org.firstinspires.ftc.teamcode.v2.components

import org.firstinspires.ftc.teamcode.v2.components.superclasses.ProgramComponent
import org.firstinspires.ftc.teamcode.v2.components.util.ArtefactColor
import org.firstinspires.ftc.teamcode.v2.components.util.Calculations
import org.firstinspires.ftc.teamcode.v2.modules.CameraOnGrab
import org.firstinspires.ftc.teamcode.v2.modules.Grab
import org.firstinspires.ftc.teamcode.v2.modules.superclasses.RobotPack
import org.firstinspires.ftc.teamcode.v2.util.dashconfigs.ConfigGrabCam
import org.firstinspires.ftc.teamcode.v2.util.dashconfigs.ConfigGrabController
import org.opencv.core.Mat
import org.opencv.core.Point
import org.opencv.core.Scalar
import org.opencv.core.Size
import org.opencv.imgproc.Imgproc
import org.openftc.easyopencv.OpenCvPipeline
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.channels.FileChannel
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class YoloRealisation(val P: RobotPack) {

    class Detection(val x1: Double, val y1: Double, val x2: Double, val y2: Double, val conf: Double) {}

    var interpreter: Interpreter? = null;
    val IMGSZ = 640;
    val confTh = ConfigGrabController.confTh;
    val iouTh = ConfigGrabController.iouTh;

    init {
        val weights = P.hwmp.appContext.assets.openFd("best_float32_grab.tflite")
        val inputStream = FileInputStream(weights.fileDescriptor);
        val channel = inputStream.channel;
        val model = channel.map(FileChannel.MapMode.READ_ONLY, weights.startOffset, weights.declaredLength);
        val modelOptions = Interpreter.Options();
        modelOptions.setNumThreads(2);
        interpreter = Interpreter(model, modelOptions);
    }

    fun detect(inputMat: Mat): ArrayList<Detection> {
        Imgproc.resize(inputMat, inputMat, Size(IMGSZ.toDouble(), IMGSZ.toDouble()));

        val input = ByteBuffer.allocateDirect(3 * IMGSZ * IMGSZ * 4);
        input.order(ByteOrder.nativeOrder());
        for ( x in 0..<IMGSZ ) {
            for ( y in 0..<IMGSZ ) {
                input.putFloat(inputMat.get(x, y)[2].toFloat()/255f);
                input.putFloat(inputMat.get(x, y)[1].toFloat()/255f);
                input.putFloat(inputMat.get(x, y)[0].toFloat()/255f);
            }
        }
        input.rewind();

        var output: Array<Array<Array<Float>>> = arrayOf();

        interpreter?.run(input, output);

        val detectionsAll: ArrayList<Detection> = arrayListOf();

        for ( detectionRaw in output[0] ) { // cx, cy, w, h, conf
            if ( detectionRaw[4] < confTh ) { continue; }
            detectionsAll.add(Detection(
                (detectionRaw[0] - detectionRaw[2]/2).toDouble(),
                (detectionRaw[1] - detectionRaw[3]/2).toDouble(),
                (detectionRaw[0] + detectionRaw[2]/2).toDouble(),
                (detectionRaw[1] + detectionRaw[3]/2).toDouble(),
                detectionRaw[4].toDouble()
            ))
        }

        val detections: ArrayList<Detection> = arrayListOf();
        val sortedAllDetections = detectionsAll.sortedByDescending { it.conf }.toMutableList();

        while ( sortedAllDetections.size != 0 ) {
            val box1 = sortedAllDetections.removeAt(0);
            detections.add(box1);
            val neighbors = sortedAllDetections.iterator();
            while ( neighbors.hasNext() ) {
                if ( rateIoU(box1, neighbors.next()) > iouTh ) { neighbors.remove(); }
            }
        }

        return detections;
    }

    private fun rateIoU(box1: Detection, box2: Detection): Double {
        val S1 = (box1.x2-box1.x1)*(box1.y2-box1.y1);
        val S2 = (box2.x2-box2.x1)*(box2.y2-box2.y1);
        val maxOfX1 = max(box1.x1, box2.x1);
        val maxOfY1 = max(box1.y1, box2.y1);
        val minOfX2 = min(box1.x2, box2.x2);
        val minOfY2 = min(box1.y2, box2.y2);
        val intersection = max(minOfX2-maxOfX1, 0.0)*max(minOfY2-maxOfY1, 0.0);
        val union = S1+S2-intersection;
        return intersection/union;
    }
}

class ColorDetectorMedian() {
    companion object {
        fun detect(input: Mat, detection: YoloRealisation.Detection): ArtefactBox {
            val reds: ArrayList<Double> = arrayListOf();
            val greens: ArrayList<Double> = arrayListOf();
            val blues: ArrayList<Double> = arrayListOf();
            for (x in detection.x1.toInt()..<detection.x2.toInt()) {
                for (y in detection.y1.toInt()..<detection.y2.toInt()) {
                    reds.add(input.get(x, y)[2]);
                    greens.add(input.get(x, y)[1]);
                    blues.add(input.get(x, y)[0]);
                }
            }
            val medianPixelIndex: Int = reds.size / 2
            val medianColors = arrayOf(
                reds.sorted()[medianPixelIndex],
                greens.sorted()[medianPixelIndex],
                blues.sorted()[medianPixelIndex]
            );
            val purpleRate: Double =
                (medianColors[0] + 255 - medianColors[1] + medianColors[2]) / 3;
            val greenRate: Double =
                (255 - medianColors[0] + medianColors[1] + 255 - medianColors[2]) / 3;
            return ArtefactBox(
                detection.x1, detection.y1, detection.x2, detection.y2,
                if (purpleRate > greenRate) ArtefactColor.PURPLE else ArtefactColor.GREEN
            )
        }
    }
}

class ArtefactBox(val x1: Double, val y1: Double, val x2: Double, val y2: Double, val color: ArtefactColor) {}

class GrabControllerCamPipeline(val yoloDetector: YoloRealisation) : OpenCvPipeline() {
    var detections: ArrayList<ArtefactBox> = arrayListOf();
    override fun processFrame(input: Mat?): Mat? {
        if (input != null) {
            val dd: ArrayList<ArtefactBox> = arrayListOf();
            for ( d in yoloDetector.detect(input) ) {
                dd.add(ColorDetectorMedian.detect(input, d))
            }
            detections = dd;
        } else { detections = arrayListOf(); }
        return input;
    }
}

class GrabControllerCamPipelineTest(val yoloDetector: YoloRealisation): OpenCvPipeline() {
    override fun processFrame(input: Mat?): Mat? {
        if (input != null) {
            for ( d in yoloDetector.detect(input) ) {
                Imgproc.rectangle(input, Point(d.x1, d.y1), Point(d.x2, d.y2), if ( ColorDetectorMedian.detect(input, d).color == ArtefactColor.GREEN ) Scalar(0.0, 255.0, 0.0) else Scalar(255.0, 0.0, 255.0), 3);
                Imgproc.putText(input, d.conf.toString(), Point(d.x1, d.y1-30.0), Imgproc.FONT_HERSHEY_PLAIN, 25.0, Scalar(128.0, 255.0, 128.0), 3);
            }
        }
        return input;
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
    private val yoloDetector = YoloRealisation(P);
    private val pipe = GrabControllerCamPipeline(yoloDetector);
    private val pipeTest = GrabControllerCamPipelineTest(yoloDetector);

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
                        break; // А что будет если таких будет несколько? А я откуда знаю (да, если что я сам с собой)
                    }
                }
            }
        }
    }

}