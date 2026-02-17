package org.firstinspires.ftc.teamcode.v2.modules

import com.qualcomm.robotcore.hardware.Servo
import org.firstinspires.ftc.teamcode.v2.modules.superclasses.Module
import org.firstinspires.ftc.teamcode.v2.modules.superclasses.RobotPack

class Tolkalka(P: RobotPack) : Module(P) {
    val TLK_L = P.hwmp.get(Servo::class.java, "TLK_L");
    val TLK_R = P.hwmp.get(Servo::class.java, "TLK_R");

    fun servoDown() { TLK_L.position = TLK_L_DWN; TLK_R.position = TLK_R_DWN }
    fun servoUp() { TLK_R.position = TLK_R_UP; TLK_L.position = TLK_L_UP }

    val TLK_L_UP = 1.0;
    val TLK_L_DWN = .2;
    val TLK_R_UP = 0.0;
    val TLK_R_DWN = .8;

}