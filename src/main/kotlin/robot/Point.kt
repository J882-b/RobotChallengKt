package robot

class Point(val x: Byte, val y: Byte) {
    fun withOffset(direction: Any): Point {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        fun random(dimension: Dimension): Point {
            // TODO
            return Point(8 , 8)
        }
    }
}
