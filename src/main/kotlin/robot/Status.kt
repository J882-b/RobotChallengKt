package robot

/*
    function Status(tank) {
        this.direction = tank.direction;
        this.point = tank.point;
        this.energy = tank.energy;
    }
    Status.prototype.getDirection = function() {
        return this.direction;
    };
    Status.prototype.getLocation = function() {
        return this.point;
    };
    Status.prototype.isAlive = function() {
        return 0 < this.energy;
    };
 */
class Status {
    private val direction: Direction
    private val point: Point
    private val energy: Int

    constructor(tank: Tank) {
        direction = tank.direction
        point = tank.point
        energy = tank.energy
    }

    fun getDirection() = direction
    fun getLocation() = point
    fun isAlive() = 0 < energy
}
