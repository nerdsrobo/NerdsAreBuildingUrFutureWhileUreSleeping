package org.firstinspires.ftc.teamcode.modules.superclasses

open class RobotCreator(val P: RobotPack) {
    fun createByConfig(config: Array<Module>) {
        config.forEach { module -> module.initModule() }
    }

}