package robot

data class Point(val x: Int, val y: Int) {
    fun withOffset(direction: Direction, offset: Int = 1) =
            Point(x + direction.x * offset, y + direction.y * offset)

    companion object {
        fun random(dimension: Dimension)= Point(random(dimension.width), random(dimension.height))
    }
}
