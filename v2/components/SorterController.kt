package org.firstinspires.ftc.teamcode.v2.components

import org.firstinspires.ftc.teamcode.util.Utils
import org.firstinspires.ftc.teamcode.v2.components.superclasses.PD
import org.firstinspires.ftc.teamcode.v2.components.superclasses.ProgramComponent
import org.firstinspires.ftc.teamcode.v2.components.util.ArtefactColor
import org.firstinspires.ftc.teamcode.v2.modules.Sorter
import org.firstinspires.ftc.teamcode.v2.modules.superclasses.RobotPack
import org.firstinspires.ftc.teamcode.v2.util.Configurable
import kotlin.math.abs

enum class DaMode {
    GRAB, SHOOT
}

class SorterController(P: RobotPack) : ProgramComponent(P) {
    var srt: Sorter = requestModule(Sorter::class) as Sorter;

    var slots: ArrayList<ArtefactColor> = arrayListOf(ArtefactColor.NULL, ArtefactColor.NULL, ArtefactColor.NULL);

    val perSlotTicks = Configurable.SrtControlTicksPerSlot;

    val changeDaModeTicks = Configurable.SrtControlChangeDaModeTicks;

    var daMode: DaMode = DaMode.SHOOT;

    var control = true;

    var targetTicks = 0;
    val srtPd = PD(Configurable.SrtControlKP, Configurable.SrtControlKD);

    fun getToPosition(ticks: Int) {
        targetTicks += ticks;
    }

    fun slotSwap(slots: Int) {
        getToPosition(slots * perSlotTicks);
    }

    fun findNext(color: ArtefactColor) {
        if ( slots[0] == color ) { }
        else if ( slots[1] == color ) { slotSwap(1); slots = arrayListOf(slots[1], slots[2], slots[0]); }
        else if ( slots[2] == color ) { slotSwap(-1); slots = arrayListOf(slots[2], slots[0], slots[1]); }
    }

    fun ifGotToPosition(): Boolean {
        return ( abs(targetTicks - srt.getSrtTicks()) < Configurable.SIXSEVEN );
    }

    fun changeDaMode(mode: DaMode) {
        if ( daMode == DaMode.SHOOT && mode == DaMode.GRAB ) { getToPosition(-changeDaModeTicks); daMode = mode; }
        else if ( daMode == DaMode.GRAB && mode == DaMode.SHOOT ) { getToPosition(changeDaModeTicks); daMode = mode; }
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

    var lastU = 0.0;

    val util = Utils()

    override fun tick() {
        //P.telemetry.addData("asd", targetTicks - srt.getSrtTicks().toDouble());
        lastU = srtPd.tick(targetTicks - srt.getSrtTicks().toDouble());
        //P.telemetry.addData("asdsda", lastU);
        //P.telemetry.update();
        if ( control ) { srt.setSrtPower(util.absSign(lastU, .44)); }
        if ( ifGotToPosition() ) { control=false; srt.setSrtPower(0.0)}
        else { control = true; }
    }
}