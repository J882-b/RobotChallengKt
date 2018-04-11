package robot

class Input {
    private val playfieldDimension: Dimension
    private val ownStatus: Status
    private val opponentStatus: List<Status>


    constructor(tank: Tank, board: Board) {
        playfieldDimension = Dimension(board.dimension.width, board.dimension.height)
        ownStatus = Status(board.tanks.filter{t -> t.point == tank.point }[0])
        opponentStatus = board.tanks.filter {t -> t.point != tank.point }.map{t -> Status(t)}
    }

    fun getPlayfieldSize() = playfieldDimension
    fun getFireRange() = Tank.fireRange
    fun getOwnStatus() = ownStatus
    fun getOpponentStatus() = opponentStatus
}
