package robot

/*
    function Point(x, y) {
        this.x = x;
        this.y = y;
    }
    Point.prototype.equals = function(that) {
        if (typeof that === 'undefined') {
            var e = new Error('that is undefined');
            console.log(e.stack);
        }
        return this.x === that.x && this.y === that.y;
    };
    Point.prototype.random = function(dimension) {
        return new Point(random(dimension.width), random(dimension.height));
    };
    Point.prototype.withOffset = function(direction, offset) {
        if (typeof offset === 'undefined') {
            offset = 1;
        }
        return new Point(this.x + direction.x * offset, this.y + direction.y * offset);
    };
 */
data class Point(val x: Int, val y: Int) {
    fun withOffset(direction: Direction, offset: Int = 1) =
            Point(x + direction.x * offset, y + direction.y * offset)

    companion object {
        fun random(dimension: Dimension)= Point(random(dimension.width), random(dimension.height))
    }
}
