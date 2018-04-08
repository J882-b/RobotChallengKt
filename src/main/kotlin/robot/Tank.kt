package robot

import robot.strategy.Strategy

class Tank(board: Board, val strategy: Strategy) {
    val imageId: String = ""
    var energy: Int = 5
    var hits: Int = 0
    var frags: Int = 0
    val point: Point = Point(0, 0)
    val direction: Direction = Direction()

    fun isAlive(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun move(board: Board) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun hitReset() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun hit(opposite: Any) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        fun fireRange(): Int {
            return 10
        }
    }

}
