package robot

class Direction {
    val angle: Int = 0
    val name: String = ""

    fun opposite(): Direction {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun counterClockwise(): Direction {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun clockwise(): Direction {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        fun random(): Direction {
            return Direction()
        }
    }

}
