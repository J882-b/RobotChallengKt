package robot.strategy

import robot.*

/*
    function FireFire() {
        var width = 0;
        var height = 0;
        var fireRange = 0;
        var myStatus = {};
        var otherStatus = [];
        var myLocation = {};
        var myDirection = {};
        var myPosition = {};
        var alivePositions = [];
        var deadPositions = [];

        this.name = 'FireFire';
        this.author = 'JMH__';
        this.getNextMove = function(input) {
            // var startTime = new Date().getTime();
            stateUpdate(input);
            var visited = deadPositions.concat(alivePositions);
            var move = findMoveToClosestFire(myPosition, alivePositions, visited);
            // var endTime = new Date().getTime();
            // console.log('Time in FireFire: ' + (endTime - startTime));
            return move;
        };

        var stateUpdate = function(input) {
            width = input.getPlayfieldSize().width;
            height = input.getPlayfieldSize().height;
            fireRange = input.getFireRange();
            myStatus = input.getOwnStatus();
            otherStatus = input.getOpponentStatus();
            myDirection = myStatus.getDirection();
            myLocation = myStatus.getLocation();
            myPosition = pos(myLocation, myDirection);
            alivePositions = [];
            deadPositions = [];
            otherStatus.forEach(function(s) {
                if (s.isAlive()) {
                    alivePositions = alivePositions.concat(positionList(s.getLocation()));
                } else {
                    deadPositions = deadPositions.concat(positionList(s.getLocation()));
                }
            });
        };

        var findMoveToClosestFire = function(root, search, visited) {
            var queue = [];
            queue.push(root);
            while (queue.length > 0) {
                var cp = queue.shift();
                visited.push(cp);
                if (posIsFirePosition(cp, search)) {
                    cp.moves.push(Move.prototype.fire);
                    return cp.moves[0];
                } else {
                    var newPositions = [ posMove(cp), posClockwise(cp), posCounterClockwise(cp) ];
                    for ( var i = 0; i < newPositions.length; i += 1) {
                        if (posValid(newPositions[i])) {
                            var newPositionInVisited = false;
                            for ( var j = 0; j < visited.length; j += 1) {
                                if (posEquals(visited[j], newPositions[i])) {
                                    newPositionInVisited = true;
                                }
                            }
                            if (!newPositionInVisited) {
                                queue.push(newPositions[i]);
                            }
                        }
                    }
                }
            }
            return Move.prototype.forward;
        };

        var positionList = function(point) {
            var positions = [];
            positions.push(pos(point, Direction.prototype.east));
            positions.push(pos(point, Direction.prototype.north));
            positions.push(pos(point, Direction.prototype.south));
            positions.push(pos(point, Direction.prototype.west));
            return positions;
        };

        var pos = function(point, direction, moves) {
            var _pos = {};
            _pos.point = point;
            _pos.direction = direction;
            if (typeof moves === 'undefined') {
                _pos.moves = [];
            } else {
                _pos.moves = moves;
            }
            return _pos;
        };
        var posValid = function(_pos) {
            return 0 <= _pos.point.x && _pos.point.x < width && 0 <= _pos.point.y
                    && _pos.point.y < height;
        };
        var posEquals = function(_pos, that) {
            return _pos.point.equals(that.point) && _pos.direction.equals(that.direction);
        };
        var posFire = function(_pos) {
            var positions = [];
            for ( var i = 1; i <= fireRange; i++) {
                var test = pos(_pos.point.withOffset(_pos.direction, i), _pos.direction);
                var testInDeadPositions = false;
                for ( var j = 0; j < deadPositions.length; j += 1) {
                    if (posEquals(deadPositions[j], test)) {
                        testInDeadPositions = true;
                    }
                }
                if (testInDeadPositions) {
                    break;
                } else {
                    positions.push(test);
                }
            }
            return positions;
        };
        var posIsFirePosition = function(_pos, search) {
            var possible = posFire(_pos);
            for ( var i = 0; i < possible.length; i += 1) {
                var possibleInSearch = false;
                for ( var j = 0; j < search.length; j += 1) {
                    if (posEquals(possible[i], search[j])) {
                        possibleInSearch = true;
                    }
                }
                if (possibleInSearch) {
                    return true;
                }
            }
            return false;
        };
        var posMove = function(_pos) {
            var moves = [];
            moves = moves.concat(_pos.moves);
            moves.push(Move.prototype.forward);
            return pos(_pos.point.withOffset(_pos.direction), _pos.direction, moves);
        };
        var posClockwise = function(_pos) {
            var moves = [];
            moves = moves.concat(_pos.moves);
            moves.push(Move.prototype.turnRight);
            return pos(_pos.point, _pos.direction.clockwise(), moves);
        };
        var posCounterClockwise = function(_pos) {
            var moves = [];
            moves = moves.concat(_pos.moves);
            moves.push(Move.prototype.turnLeft);
            return pos(_pos.point, _pos.direction.counterClockwise(), moves);
        };
    }
    FireFire.prototype = new Strategy();
    FireFire.prototype.constructor = FireFire;
 */
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
        console.log("FireFire getNextMove()")
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
        console.log("FireFire findMoveToClosestFire()")
        val queue = mutableListOf<Position>()
        queue.add(root)
        while (queue.size > 0) {
            console.log("FireFire findMoveToClosestFire() queue.size=${queue.size}")
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