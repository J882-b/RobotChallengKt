package robot.strategy

import robot.*

class FireFire : Strategy() {
    override val name = "FireFire"
    override val author = "JMH__"
    private var width = 0
    private var height = 0
    private var fireRange = 0
    private var myStatus : Status? = null
    private var otherStatus = listOf<Status>()
    private var myLocation = Point(0, 0)
    private var myDirection = Direction.NORTH
    private var myPosition = Position(Point(0, 0), Direction.NORTH)
    private var alivePositions = mutableListOf<Position>()
    private var deadPositions = mutableListOf<Position>()

    override fun getNextMove(input: Input): Move {
        // var startTime = new Date().getTime()
        stateUpdate(input)
        var visited = mutableListOf<Position>()
        visited.addAll(deadPositions)
        visited.addAll(alivePositions)
        var move = findMoveToClosestFire(myPosition, alivePositions, visited)
        // var endTime = new Date().getTime()
        // console.log('Time in FireFire: ' + (endTime - startTime))
        return move
    }

    fun stateUpdate(input: Input) {
        width = input.getPlayfieldSize().width
        height = input.getPlayfieldSize().height
        fireRange = input.getFireRange()
        myStatus = input.getOwnStatus()
        otherStatus = input.getOpponentStatus()
        myDirection = myStatus!!.getDirection()
        myLocation = myStatus!!.getLocation()
        myPosition = Position(myLocation, myDirection)
        alivePositions.clear()
        deadPositions.clear()
        otherStatus.forEach {s ->
            if (s.isAlive()) {
                alivePositions.addAll(positionList(s.getLocation()))
            } else {
                deadPositions.addAll(positionList(s.getLocation()))
            }
        }
    }

    private fun findMoveToClosestFire(root: Position, search: MutableList<Position>,
                                      visited: MutableList<Position>): Move {
        val queue = mutableListOf<Position>()
        queue.add(root)
        while (queue.size > 0) {
            var cp = queue.shift()
            visited.add(cp!!)
            if (cp.isFirePosition(search)) {
                cp.moves.add(Move.FIRE)
                return cp.moves[0]
            } else {
                var newPositions =
                        mutableListOf( cp.move(), cp.clockwise(), cp.counterClockwise())
                for (i in 0..(newPositions.size - 1)) {
                    if (newPositions[i].isValid() && !visited.contains(newPositions[i])) {
                        queue.add(newPositions[i])
                    }
                }
            }
        }
        return Move.FORWARD
    }

    private fun positionList(point: Point) =
            listOf(Position(point, Direction.EAST), Position(point, Direction.NORTH),
                    Position(point, Direction.SOUTH), Position(point, Direction.WEST))

    inner class Position {
        var moves = mutableListOf<Move>()
        val point: Point
        val direction: Direction

        constructor(point: Point, direction: Direction, moves: MutableList<Move> = mutableListOf()) {
            this.point = point
            this.direction = direction
            this.moves = moves
        }

        fun move(): Position {
            val moves = mutableListOf<Move>()
            moves.addAll(this.moves)
            moves.add(Move.FORWARD)
            return Position(point.withOffset(direction), direction, moves)
        }

        fun clockwise(): Position {
            val moves = mutableListOf<Move>()
            moves.addAll(this.moves)
            moves.add(Move.TURN_RIGHT)
            return Position(point, direction.clockwise(), moves);
        }

        fun counterClockwise(): Position {
            val moves = mutableListOf<Move>()
            moves.addAll(this.moves)
            moves.add(Move.TURN_LEFT);
            return Position(point, direction.counterClockwise(), moves)
        }

        fun fire(): MutableList<Position> {
            val positions = mutableListOf<Position>()
            var testInDeadPositions: Boolean
            for (i in 1..fireRange) {
                var test = Position(point.withOffset(direction, i), direction)
                testInDeadPositions = false
                for (j in 0.. (deadPositions.size - 1)) {
                    if (deadPositions[j] == test) {
                        testInDeadPositions = true
                    }
                }
                if (testInDeadPositions) {
                    break
                } else {
                    positions.add(test)
                }
            }
            return positions
        }

        fun isFirePosition(search: MutableList<Position>): Boolean {
            var possible = fire()
            for(i in 0..(possible.size - 1)) {
                var possibleInSearch = false
                for (j in 0..(search.size - 1)) {
                    if (possible[i] == search[j]) {
                        possibleInSearch = true
                    }
                }
                if (possibleInSearch) {
                    return true
                }
            }
            return false
        }

        fun isValid() = point.x in 0..(width - 1) && point.y in 0..(height - 1)

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other == null || this::class.js != other::class.js) return false

            other as Position

            if (point != other.point) return false
            if (direction != other.direction) return false

            return true
        }

        override fun hashCode(): Int {
            var result = point.hashCode()
            result = 31 * result + direction.hashCode()
            return result
        }


    }
}