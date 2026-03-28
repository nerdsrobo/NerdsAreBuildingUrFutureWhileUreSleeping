package org.firstinspires.ftc.teamcode.v2.modules

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DigitalChannel
import com.qualcomm.robotcore.hardware.Servo
import org.firstinspires.ftc.teamcode.modules.sort.versions.SortSingleAndServosV1.Companion.kSRT
import org.firstinspires.ftc.teamcode.v2.components.util.UsefulFuncs
import org.firstinspires.ftc.teamcode.v2.modules.superclasses.Module
import org.firstinspires.ftc.teamcode.v2.modules.superclasses.RobotPack
import kotlin.math.abs

class Sorter(P: RobotPack) : Module(P) {
    val SRT = P.hwmp.get(DcMotor::class.java, "SRT");

    val HLD_GRB_L = P.hwmp.get(Servo::class.java, "HLD_GRB_L");
    val HLD_GRB_R = P.hwmp.get(Servo::class.java, "HLD_GRB_R");
    val HLD_SHT   = P.hwmp.get(Servo::class.java, "HLD_SHT");

    val btn = P.hwmp.get(DigitalChannel::class.java, "srt");

    val grb_l_closed = 0.2;
    val grb_r_closed = 0.0;
    val grb_l_opened = 0.0;
    val grb_r_opened = 0.2;

    val sht_closed = 0.0;
    val sht_opened = 0.2;

    init {
        SRT.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE;
        SRT.mode = DcMotor.RunMode.RUN_USING_ENCODER;

        btn.mode = DigitalChannel.Mode.INPUT;
    }

    fun setSrtPower(pw: Double) {
        //SRT.power = abs(UsefulFuncs.absSign(pw, 1.0) * kSRT)
        SRT.power = UsefulFuncs.absSign(pw, .8);
    }

    fun getSrtTicks(): Int {
        return SRT.currentPosition;
    }

    fun getBtnState(): Boolean {
        return btn.state;
    }

    fun closeGrab() { HLD_GRB_L.position = grb_l_closed; HLD_GRB_R.position = grb_r_closed; }
    fun openGrab() { HLD_GRB_L.position = grb_l_opened; HLD_GRB_R.position = grb_r_opened; }

    fun closeSht() { HLD_SHT.position = sht_closed; }
    fun openSht() { HLD_SHT.position = sht_opened; }

}