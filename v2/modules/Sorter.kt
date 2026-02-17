package org.firstinspires.ftc.teamcode.v2.modules

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DigitalChannel
import com.qualcomm.robotcore.hardware.Servo
import org.firstinspires.ftc.teamcode.v2.modules.superclasses.Module
import org.firstinspires.ftc.teamcode.v2.modules.superclasses.RobotPack

class Sorter(P: RobotPack) : Module(P) {
    val SRT = P.hwmp.get(DcMotor::class.java, "SRT");

    val BTN_SRT = P.hwmp.get(DigitalChannel::class.java, "BTN_SRT");

    val HLD_GRB_L = P.hwmp.get(Servo::class.java, "HLD_GRB_L");
    val HLD_GRB_R = P.hwmp.get(Servo::class.java, "HLD_GRB_R");
    val HLD_SHT   = P.hwmp.get(Servo::class.java, "HLD_SHT");

    val kSRT = -1.0;

    val grb_l_closed = 0.5;
    val grb_r_closed = 0.5;
    val grb_l_opened = 0.0;
    val grb_r_opened = 0.0;

    val sht_closed = 0.0;
    val sht_opened = 0.2;

    init {
        SRT.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE;
        SRT.mode = DcMotor.RunMode.RUN_USING_ENCODER;
        BTN_SRT.mode = DigitalChannel.Mode.INPUT;
    }

    fun setSrtPower(pw: Double) {
        SRT.power = pw * kSRT;
    }

    fun getBtnState(): Boolean {
        return BTN_SRT.state;
    }

    fun getSrtTicks(): Int {
        return SRT.currentPosition;
    }

    fun closeGrab() { HLD_GRB_L.position = grb_l_closed; HLD_GRB_R.position = grb_r_closed; }
    fun openGrab() { HLD_GRB_L.position = grb_l_opened; HLD_GRB_R.position = grb_r_opened; }

    fun closeSht() { HLD_SHT.position = sht_closed; }
    fun openSht() { HLD_SHT.position = sht_opened; }

}