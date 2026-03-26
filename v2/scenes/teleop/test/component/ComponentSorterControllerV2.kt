package org.firstinspires.ftc.teamcode.v2.scenes.teleop.test.component

import com.acmerobotics.dashboard.FtcDashboard
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.v2.components.DaMode
import org.firstinspires.ftc.teamcode.v2.components.SorterControllerV2
import org.firstinspires.ftc.teamcode.v2.components.util.ArtefactColor
import org.firstinspires.ftc.teamcode.v2.modules.Grab
import org.firstinspires.ftc.teamcode.v2.modules.ShooterV2
import org.firstinspires.ftc.teamcode.v2.modules.Sorter
import org.firstinspires.ftc.teamcode.v2.modules.TolkalkaV2
import org.firstinspires.ftc.teamcode.v2.modules.superclasses.RobotPack
import org.firstinspires.ftc.teamcode.v2.scenes.superclasses.TeleOpPacker

@TeleOp(name = "COMPONENT - SorterControllerV2", group = "component")
class ComponentSorterControllerV2(): TeleOpPacker() {
    override fun init_(P: RobotPack) {
        val dashTelemetry = FtcDashboard.getInstance().telemetry;

        val srt = Sorter(P);
        val tlk = TolkalkaV2(P);
        val grb = Grab(P);
        val sht = ShooterV2(P);
        val srtControl = SorterControllerV2(P);
        linkerApi.activeComponents.add(srtControl);

        linkerApi.bindVoid({true}, {true}, {
            dashTelemetry.addData("slot0", srtControl.slots[0]);
            dashTelemetry.addData("slot1", srtControl.slots[1]);
            dashTelemetry.addData("slot2", srtControl.slots[2]);
            dashTelemetry.addData("daMode", srtControl.daMode);
            dashTelemetry.addData("targetTicks", srtControl.targetTicks);
            dashTelemetry.addData("taskSlots", srtControl.taskSlots);
            dashTelemetry.addData("lastU", srtControl.lastU);
            dashTelemetry.addData("isSorting", srtControl.isSorting());
            dashTelemetry.update();
        })

        linkerApi.bindFlag({true}, {gamepad1.left_bumper}, {srtControl.slotSwap(-1);});
        linkerApi.bindFlag({true}, {gamepad1.right_bumper}, {srtControl.slotSwap(1);});

        linkerApi.bindFlag({true}, {gamepad1.dpad_up}, {srtControl.findNext(ArtefactColor.PURPLE)});
        linkerApi.bindFlag({true}, {gamepad1.dpad_down}, {srtControl.findNext(ArtefactColor.GREEN)});

        linkerApi.bindFlag({true}, {gamepad1.dpad_left}, {srtControl.eatAndSwap(ArtefactColor.PURPLE)});
        linkerApi.bindFlag({true}, {gamepad1.dpad_right}, {srtControl.eatAndSwap(ArtefactColor.GREEN)});

        linkerApi.bindFlag({true}, {gamepad1.a}, {srtControl.changeDaMode(DaMode.GRAB)});
        linkerApi.bindFlag({true}, {gamepad1.y}, {srtControl.changeDaMode(DaMode.SHOOT)});

        linkerApi.bindVoid({true}, {gamepad1.x}, {tlk.setServPower(1.0)});
        linkerApi.bindVoid({true}, {gamepad1.b}, {tlk.setServPower(0.0)});

        linkerApi.bindFlag({true}, {gamepad1.right_stick_y < -.7}, {srtControl.servosToSort()});
        linkerApi.bindFlag({true}, {gamepad1.right_stick_x > .7}, {srtControl.servosToShoot()});
        linkerApi.bindFlag({true}, {gamepad1.right_stick_x < -.7}, {srtControl.servosToGrab()});

        linkerApi.bindFlag({true}, {gamepad1.left_stick_y < -.7}, {grb.setGrbPower(1.0)});
        linkerApi.bindFlag({true}, {gamepad1.left_stick_y > .7}, {grb.setGrbPower(0.0)});
        linkerApi.bindFlag({true}, {gamepad1.left_stick_x > .7}, {sht.setShtPower(.7)});
        linkerApi.bindFlag({true}, {gamepad1.left_stick_x < -.7}, {sht.setShtPower(0.0)});
    }
}