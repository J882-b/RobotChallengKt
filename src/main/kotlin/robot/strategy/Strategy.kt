package robot.strategy

import robot.Input
import robot.Move

abstract class Strategy {
    abstract fun getNextMove(input: Input): Move

    abstract val name: String
    abstract val author: String
}
