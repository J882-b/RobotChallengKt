package robot

/*
function Direction() {
    }

    function North() {
        this.name = 'North';
        this.angle = 0;
        this.x = 0;
        this.y = -1;
    }
    North.prototype = new Direction();
    North.prototype.constructor = North;
    North.prototype.clockwise = function() {
        return this.east;
    };
    North.prototype.counterClockwise = function() {
        return this.west;
    };
    North.prototype.opposite = function() {
        return this.south;
    };

    function East() {
        this.name = 'East';
        this.angle = 90;
        this.x = 1;
        this.y = 0;
    }
    East.prototype = new Direction();
    East.prototype.constructor = East;
    East.prototype.clockwise = function() {
        return this.south;
    };
    East.prototype.counterClockwise = function() {
        return this.north;
    };
    East.prototype.opposite = function() {
        return this.west;
    };

    function South() {
        this.name = 'South';
        this.angle = 180;
        this.x = 0;
        this.y = 1;
    }
    South.prototype = new Direction();
    South.prototype.constructor = South;
    South.prototype.clockwise = function() {
        return this.west;
    };
    South.prototype.counterClockwise = function() {
        return this.east;
    };
    South.prototype.opposite = function() {
        return this.north;
    };

    function West() {
        this.name = 'West';
        this.angle = 270;
        this.x = -1;
        this.y = 0;
    }
    West.prototype = new Direction();
    West.prototype.constructor = West;
    West.prototype.clockwise = function() {
        return this.north;
    };
    West.prototype.counterClockwise = function() {
        return this.south;
    };
    West.prototype.opposite = function() {
        return this.east;
    };

    Direction.prototype.north = new North();
    Direction.prototype.east = new East();
    Direction.prototype.south = new South();
    Direction.prototype.west = new West();
    Direction.prototype.random = function() {
        switch (random(4)) {
        case 0:
            return Direction.prototype.north;
        case 1:
            return Direction.prototype.east;
        case 2:
            return Direction.prototype.south;
        case 3:
            return Direction.prototype.west;
        case 4:
        }
    };
    Direction.prototype.equals = function(that) {
        return this.name === that.name;
    };
 */
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
        fun random() = Direction.values()[random(Direction.values().size)]
    }
}
