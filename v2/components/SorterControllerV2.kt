package org.firstinspires.ftc.teamcode.v2.components

import org.firstinspires.ftc.teamcode.v2.components.superclasses.PD
import org.firstinspires.ftc.teamcode.v2.components.superclasses.ProgramComponent
import org.firstinspires.ftc.teamcode.v2.components.util.ArtefactColor
import org.firstinspires.ftc.teamcode.v2.components.util.UsefulFuncs
import org.firstinspires.ftc.teamcode.v2.modules.Sorter
import org.firstinspires.ftc.teamcode.v2.modules.superclasses.RobotPack
import org.firstinspires.ftc.teamcode.v2.util.dashconfigs.ConfigSorterController
import org.firstinspires.ftc.teamcode.v2.util.dashconfigs.ConfigSorterControllerV2
import kotlin.math.abs

class SorterControllerV2(P: RobotPack): ProgramComponent(P) {
    private val srt = requestModule(Sorter::class) as Sorter;

    var slots: ArrayList<ArtefactColor> = arrayListOf(
        ArtefactColor.NULL, ArtefactColor.NULL, ArtefactColor.NULL);

    var daMode: DaMode = DaMode.GRAB;

    var controlPD = false;

    var runToSwitch = false
    var runToSwitchDirection = 0
    var taskSlots = 0

    fun slotSwap(slots: Int) {
        getToPosition(ConfigSorterControllerV2.ticksPerSlot * slots);
        taskSlots = abs(slots);
    }

    var targetTicks = 0
    val srtPd = PD(ConfigSorterControllerV2.SrtControlKP, ConfigSorterControllerV2.SrtControlKD)

    fun getToPosition(ticks: Int) {
        targetTicks = srt.getSrtTicks() + ticks
        controlPD = true
    }

    fun findNext(color: ArtefactColor) {
        if ( slots[0] == color ) { }
        else if ( slots[1] == color ) { slotSwap(1); slots = arrayListOf(slots[1], slots[2], slots[0]); }
        else if ( slots[2] == color ) { slotSwap(-1); slots = arrayListOf(slots[2], slots[0], slots[1]); }
    }

    private fun ifGotToPosition(): Boolean {
        return ( abs(targetTicks - srt.getSrtTicks()) < ConfigSorterController.SIXSEVEN );
    }

    fun changeDaMode(mode: DaMode) {
        if ( daMode == DaMode.SHOOT && mode == DaMode.GRAB ) { getToPosition(ConfigSorterControllerV2.positiveShootTicks); daMode = mode; }
        else if ( daMode == DaMode.GRAB && mode == DaMode.SHOOT ) { getToPosition(-ConfigSorterControllerV2.positiveShootTicks); daMode = mode; }
    }

    fun eatAndSwap(color: ArtefactColor) {
        slots = arrayListOf(slots[1], color, slots[0]);
        slotSwap(1);
    }

    fun eatNotSwap(color: ArtefactColor) {
        slots = arrayListOf(slots[1], color, slots[0]);
    }

    fun COOKEDANDSHOOTED() {
        slots[0] = ArtefactColor.NULL;
    }

    fun isSorting(): Boolean {
        return controlPD
    }

    fun servosToSort() {
        srt.closeGrab();
        srt.closeSht();
    }
    fun servosToShoot() {
        srt.openSht();
    }
    fun servosToGrab() {
        srt.openGrab();
    }

    var lastU = 0.0;

    override fun tick() {
        lastU = srtPd.tick(targetTicks - srt.getSrtTicks().toDouble())
        if ( controlPD ) {
            if ( ifGotToPosition() ) { controlPD = false; srt.setSrtPower(0.0) }
            else { srt.setSrtPower(UsefulFuncs.absSign(lastU, .44)) }
        }
    }
}