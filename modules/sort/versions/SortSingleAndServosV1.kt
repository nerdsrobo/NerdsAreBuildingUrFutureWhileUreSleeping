package org.firstinspires.ftc.teamcode.modules.sort.versions

import com.acmerobotics.dashboard.config.Config
import org.firstinspires.ftc.teamcode.modules.sort.SortSingleAndServos
import org.firstinspires.ftc.teamcode.modules.superclasses.RobotPack

@Config
class SortSingleAndServosV1(P: RobotPack) : SortSingleAndServos(P) {

    fun setPwSort(pw: Double) {
        SRT.power = pw*kSRT;
    }

    fun servUp() {
        TLK_L.position = TLK_L_UP;
        TLK_R.position = TLK_R_UP;
    }

    fun servDown() {
        TLK_L.position = TLK_L_DWN;
        TLK_R.position = TLK_R_DWN;
    }

    companion object {
        const val kSRT = -1.0;
        const val TLK_L_UP = 1.0;
        const val TLK_L_DWN = 0.0;
        const val TLK_R_UP = 1.0;
        const val TLK_R_DWN = 0.0;
    }

}