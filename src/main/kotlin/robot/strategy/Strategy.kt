package robot.strategy

import robot.Input
import robot.Move

abstract class Strategy {
    abstract val name: String
    abstract val author: String

    abstract fun getNextMove(input: Input): Move
}
