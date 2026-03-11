package org.firstinspires.ftc.teamcode.v2.systems

import org.firstinspires.ftc.teamcode.v2.components.DaMode
import org.firstinspires.ftc.teamcode.v2.components.GrabController
import org.firstinspires.ftc.teamcode.v2.components.LookAtGoalCalcs
import org.firstinspires.ftc.teamcode.v2.components.ShooterController
import org.firstinspires.ftc.teamcode.v2.components.SorterControllerV2
import org.firstinspires.ftc.teamcode.v2.components.util.ArtefactColor
import org.firstinspires.ftc.teamcode.v2.modules.Tolkalka
import org.firstinspires.ftc.teamcode.v2.modules.superclasses.RobotPack
import org.firstinspires.ftc.teamcode.v2.systems.superclasses.ProgramSystem
import org.firstinspires.ftc.teamcode.v2.util.dashconfigs.ConfigShooterController

class ShootingSystem(P: RobotPack) : ProgramSystem(P) {
    private val srtControl = requestComponent(SorterControllerV2::class) as SorterControllerV2;
    private val shtControl = requestComponent(ShooterController::class) as ShooterController;
    private val grbControl = requestComponent(GrabController::class) as GrabController;
    private val tlk = requestModule(Tolkalka::class) as Tolkalka;
    private val lookAtGoalCalcs = requestComponent(LookAtGoalCalcs::class) as LookAtGoalCalcs;

    var grbMove = 0;
    var artefactsInside = 0;
    var order: ArrayList<ArtefactColor> = arrayListOf();
    var executeByOrder = false;
    var shooterPreSpeed = 6.0;

    var setTargetAngleHandler = {angle: Double -> }
    var isTargetAngleReady = false;
    var blockMoving = false;
    var absX = .0;
    var absY = .0;
    var isNear = true;
    var positionFixed = false;

    var isServoReady = false;
    var startShoot = false;

    init {
        P.linkerApi.bindVoid({state == "default"}, {grbMove == 0 && artefactsInside < 3}, {grbControl.stop()});
        P.linkerApi.bindVoid({state == "default"}, {grbMove == 1 && artefactsInside < 3}, {grbControl.take()});
        P.linkerApi.bindVoid({true}, {artefactsInside >= 3}, {grbMove = 0; grbControl.spit()});
        onState("grbtosort", {
            srtControl.servosToSort();
            shtControl.stop = false;
            shtControl.control = true;
            shtControl.targetW = shooterPreSpeed
            P.linkerApi.setTimer(450, {state = "readytosort"})
        })
        onState("srttoexecute", {
            if ( executeByOrder || !positionFixed ) {
                isServoReady = false;
                srtControl.changeDaMode(DaMode.SHOOT);
                srtControl.servosToShoot();
            }
            if ( !positionFixed ) {
                val calcs = lookAtGoalCalcs.calcs(absX, absY, shooterPreSpeed, isNear);
                isTargetAngleReady = false;
                setTargetAngleHandler(calcs.rotRobot);
                shtControl.setAngle(calcs.shooterAngle)
                positionFixed = true;
            }
            blockMoving = true;
            P.linkerApi.setTimer(450, {isServoReady = true;})
        })
        P.linkerApi.bindVoid({state == "srttoexecute"}, {isServoReady && !srtControl.isSorting() && isTargetAngleReady && shtControl.isOnRevolutions()}, {state = "execute"})
        onState("execute", {
            tlk.servoUp();
            P.linkerApi.setTimer(450, {state = "executed"})
        })
        onState("executed", {
            tlk.servoDown();
            P.linkerApi.setTimer(450, {state = "shooted"})
        })
        onState("shooted", {
            artefactsInside--;
            if ( artefactsInside < 1 ) {
                blockMoving = false;
                srtControl.slots = arrayListOf(ArtefactColor.NULL, ArtefactColor.NULL, ArtefactColor.NULL)
                isServoReady = false;
                srtControl.servosToGrab();
                srtControl.changeDaMode(DaMode.GRAB);
                shtControl.stop = true;
                shtControl.control = true;
                startShoot = false;
                state = "srttogrb"
                P.linkerApi.setTimer(450, {isServoReady = true;})
            }
            else {
                if ( executeByOrder && order.size > 0 ) {
                    srtControl.COOKEDANDSHOOTED();
                    srtControl.servosToSort();
                    P.linkerApi.setTimer(450, {state = "sort"})
                }
                else { srtControl.slotSwap(1); state = "srttoexecute" }
            }
        })
        onState("sort", {
            if ( order.size < 1 ) { state = "readytoexecute"; }
            else {
                srtControl.findNext(order[0]);
                order.removeAt(0);
                state = "sorting";
            }
        })
        P.linkerApi.bindVoid({state == "sorting"}, {!srtControl.isSorting()}, {state = "readytoexecute"})
        P.linkerApi.bindVoid({state == "srttogrb"}, {!srtControl.isSorting() && isServoReady}, {state = "default"})
        P.linkerApi.bindVoid({state == "readytoexecute"}, {startShoot}, {state = "srttoexecute"})
    }

    fun grabbedArtefact() {
        srtControl.eatAndSwap(grbControl.lastSnappedColor);
        artefactsInside++;
        grbControl.swallow();
    }

    fun goSort(isNear: Boolean) {
        this.shooterPreSpeed = if ( isNear ) ConfigShooterController.nearMotorW else ConfigShooterController.farMotorW;
        this.isNear = isNear;
        state = "grbtosort";
    }

    fun placeNoOrder() {
        if (state != "readytosort") { return; }
        executeByOrder = false;
        positionFixed = false;
    }

    fun addToOrder(artefactColor: ArtefactColor) {
        if ( order.size < 3 ) { order.add(artefactColor); }
    }
    fun clearOrder() {
        order = arrayListOf();
    }
    fun placeOrder() {
        if ( state != "readytosort" ) { return; }
        executeByOrder = true;
        positionFixed = false;
        state = "sort"
    }
    fun execute(absX: Double, absY: Double) {
        if ( state != "readytosort" ) { return; }
        this.absX = absX; this.absY = absY;
        startShoot = true;
    }

    fun initSort() {
        srtControl.runToSwitch = true; srtControl.runToSwitchDirection = 1; srtControl.taskSlots = 1;
    }

    override fun tick() {

    }
}