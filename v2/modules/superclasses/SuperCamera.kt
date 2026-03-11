package org.firstinspires.ftc.teamcode.v2.modules.superclasses

import com.acmerobotics.dashboard.FtcDashboard
import org.firstinspires.ftc.robotcore.external.Telemetry
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName
import org.openftc.easyopencv.OpenCvCamera
import org.openftc.easyopencv.OpenCvCameraFactory
import org.openftc.easyopencv.OpenCvCameraRotation
import org.openftc.easyopencv.OpenCvPipeline

private class Listener(val camera: OpenCvCamera, val telemetry: Telemetry, val isStreamToDash: Boolean, val handler: () -> Unit): OpenCvCamera.AsyncCameraOpenListener {
    override fun onOpened() {
        camera.startStreaming(640, 480, OpenCvCameraRotation.UPRIGHT);
        if ( isStreamToDash ) { FtcDashboard.getInstance().startCameraStream(camera, 10.0); }
        handler();
    }
    override fun onError(errorCode: Int) {
        telemetry.addData("Camera open error, err code", errorCode);
        telemetry.update();
    }
}

open class SuperCamera(P: RobotPack, camName: String): Module(P) {
    private val cameraMonitorId = P.hwmp.appContext.resources.getIdentifier("cameraMonitorViewId", "id", P.hwmp.appContext.packageName);
    private val wbName = P.hwmp.get(WebcamName::class.java, camName);
    val camera = OpenCvCameraFactory.getInstance().createWebcam(wbName, cameraMonitorId);
    var isOpened = false;

    fun openWithPipeline(pipe: OpenCvPipeline, isStreamToDash: Boolean) {
        camera.setPipeline(pipe);
        camera.openCameraDeviceAsync(Listener(camera, P.telemetry, isStreamToDash, {isOpened = true;}));
    }

    fun close() {
        camera.closeCameraDeviceAsync { }
        isOpened = false;
    }
}