package robot

enum class Move {
    FIRE, TURN_LEFT, FORWARD, TURN_RIGHT, WAIT;

    companion object {
        fun random() = Move.values()[random(Move.values().size)]
    }
}

