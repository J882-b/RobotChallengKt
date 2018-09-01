package robot

enum class  Direction(val angle: Int, val x: Byte, val y: Byte) {
    NORTH(0, 0, -1) {
        override fun opposite() = SOUTH
        override fun counterClockwise() = WEST
        override fun clockwise() = EAST
    },
    EAST(90, 1, 0) {
        override fun opposite() = WEST
        override fun counterClockwise() = NORTH
        override fun clockwise() = SOUTH
    },
    SOUTH(180, 0,1) {
        override fun opposite() = NORTH
        override fun counterClockwise() = EAST
        override fun clockwise() = WEST
    },
    WEST(270, -1,0) {
        override fun opposite() = EAST
        override fun counterClockwise() = SOUTH
        override fun clockwise() = NORTH
    };

    abstract fun opposite(): Direction
    abstract fun counterClockwise(): Direction
    abstract fun clockwise(): Direction

    companion object {
        fun random() = Direction.values()[random(values().size)]
    }
}
