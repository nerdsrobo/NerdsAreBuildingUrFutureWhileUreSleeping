package org.firstinspires.ftc.teamcode.v2.modules

import com.qualcomm.robotcore.hardware.CRServo
import org.firstinspires.ftc.teamcode.v2.modules.superclasses.Module
import org.firstinspires.ftc.teamcode.v2.modules.superclasses.RobotPack
import org.firstinspires.ftc.teamcode.v2.util.dashconfigs.ConfigTolkalkaV2

class TolkalkaV2(P: RobotPack): Module(P) {
    val TLK_L = P.hwmp.get(CRServo::class.java, "TLK_L");
    val TLK_R = P.hwmp.get(CRServo::class.java, "TLK_R");

    fun setServPower(pw: Double) {
        TLK_R.power = pw * ConfigTolkalkaV2.kR;
        TLK_L.power = pw * ConfigTolkalkaV2.kL;
    }
}