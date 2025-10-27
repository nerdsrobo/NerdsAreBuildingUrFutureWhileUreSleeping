package org.firstinspires.ftc.teamcode.modules.superclasses

abstract class Module (val P: RobotPack) {
    init {
        initModule();
    }
    abstract fun initModule();
}