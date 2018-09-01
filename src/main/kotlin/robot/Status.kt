package robot

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
