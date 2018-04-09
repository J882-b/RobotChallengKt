package robot
/*
 function Input(tank, board) {
        this.playfieldDimension = new Dimension(board.dimension.width, board.dimension.height);
        this.ownStatus = new Status(board.tanks.filter(function(t) {
            return t.point.equals(tank.point);
        })[0]);
        this.opponentStatus = board.tanks.filter(function(t) {
            return !t.point.equals(tank.point);
        }).map(function(t) {
            return new Status(t);
        });
    }
    Input.prototype.getPlayfieldSize = function() {
        return this.playfieldDimension;
    };
    Input.prototype.getFireRange = function() {
        return Tank.prototype.fireRange;
    };
    Input.prototype.getOwnStatus = function() {
        return this.ownStatus;
    };
    Input.prototype.getOpponentStatus = function() {
        return this.opponentStatus;
    };

 */
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
