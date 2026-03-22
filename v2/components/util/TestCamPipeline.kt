package org.firstinspires.ftc.teamcode.v2.components.util

import org.firstinspires.ftc.teamcode.v2.components.ColorDetectorMedian
import org.opencv.core.Mat
import org.opencv.core.Point
import org.opencv.core.Scalar
import org.opencv.imgproc.Imgproc
import org.openftc.easyopencv.OpenCvPipeline

class TestCamPipeline() : OpenCvPipeline() {
    override fun processFrame(input: Mat?): Mat? {
        Imgproc.rectangle(input, Point(100.0, 100.0), Point(750.0, 600.0), Scalar(255.0, 255.0, 255.0), 3);
        return input;
    }
}