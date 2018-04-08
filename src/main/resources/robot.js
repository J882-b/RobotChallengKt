(function() {
    "use strict";

    document.body.onload = function() {
        new Game().start();
    };

    var random = function(n) {
        // returns an integer in the range {0, n-1}
        return Math.floor(Math.random() * n);
    };

    var path2image = function(path, callback) {
        var image = new Image();
        image.src = path;
        image.onload = callback(image);
    };

    function Game() {
        var board = new Board();

        [ new Spinner(), new Derp(), new Random(), new Random(), new Random(), new Random(),
                new Random(), new FireFire() ].forEach(function(strategy) {
            new Tank(board, strategy);
        });

        var score = new Score(board.tanks.length);

        var round = 0;
        var nextToMoveQueue = [];
        var nextMove = function() {
            var winner = false;
            if (nextToMoveQueue.length === 0) {
                round += 1;
                nextToMoveQueue = nextToMoveQueue.concat(board.getAliveTanksInRandomOrder());
                if (nextToMoveQueue.length === 1) {
                    winner = true;
                }
            }
            score.update(round, board.tanks);
            board.moveReset();
            if (!winner) {
                var tank = nextToMoveQueue.shift();

                if (tank.isAlive()) {
                    tank.move(board);
                }
                setTimeout(function() {
                    nextMove();
                }, 200);
            } else {
                alert('The winner is ' + nextToMoveQueue[0].strategy.name);
            }
        };
        this.start = function() {
            console.log('start the game');
            nextMove();
        };
    }

    function Score(players) {
        this.htmlDiv = document.getElementById('scoreDiv');
        var table = document.createElement("table");
        var headers = [ 'Image', 'Name', 'Author', 'Energy', 'Hits', 'Frags' ];
        var headerLength = headers.length;
        for ( var i = 0; i < players + 1; i += 1) {
            var tr = document.createElement("tr");
            for ( var j = 0; j < headerLength; j += 1) {
                var cell = {};
                var tag = 'td';
                if (i === 0) {
                    tag = 'th';
                }
                var tx = document.createElement(tag);
                if (j === 0 && i !== 0) {
                    cell = new Image();
                } else {
                    var text = '.';
                    if (i === 0) {
                        text = headers.shift();
                    }
                    cell = document.createTextNode(text);
                }
                tx.appendChild(cell);
                tr.appendChild(tx);
            }
            table.appendChild(tr);
        }
        table.border = "1";
        this.htmlDiv.appendChild(table);
        var roundText = document.createTextNode('Round: ');
        var roundNo = document.createTextNode('0');
        this.htmlDiv.appendChild(roundText);
        this.htmlDiv.appendChild(roundNo);

    }
    Score.prototype.update = function(round, tanks) {
        var trNodes = this.htmlDiv.childNodes[0].childNodes;
        var row = 1;
        tanks.forEach(function(tank) {
            if (round === 1) {
                trNodes[row].childNodes[0].childNodes[0].src = tank.imageId;
            }
            trNodes[row].childNodes[1].childNodes[0].data = tank.strategy.name;
            trNodes[row].childNodes[2].childNodes[0].data = tank.strategy.author;
            trNodes[row].childNodes[3].childNodes[0].data = tank.energy;
            trNodes[row].childNodes[4].childNodes[0].data = tank.hits;
            trNodes[row].childNodes[5].childNodes[0].data = tank.frags;
            row += 1;
        });
        this.htmlDiv.childNodes[2].data = round;
    };

    function Board() {
        var self = this;
        this.htmlDiv = document.getElementById('gameDiv');
        this.dimension = new Dimension(20, 20);
        this.tanks = [];
        this.htmlDiv.style.width = "400px";
        this.htmlDiv.style.height = "400px";
        this.htmlDiv.style.border = "black 5px solid";
        this.htmlDiv.style.position = "relative";

        path2image('images/game_bg.png', function(image) {
            self.htmlDiv.appendChild(image);
            image.style.position = "absolute";
            image.style.zIndex = "1";
        });

        this.laser = new Laser(this);
    }
    Board.prototype.addTank = function(tank) {
        this.tanks.push(tank);
    };
    Board.prototype.addLaser = function(laser) {
        this.laser = laser;
    };
    Board.prototype.availablePoint = function(point) {
        for ( var i = 0; i < this.tanks.length; i += 1) {
            if (this.tanks[i].point.equals(point)) {
                return false;
            }
        }
        return true;
    };
    Board.prototype.randomPoint = function() {
        var point;
        do {
            point = Point.prototype.random(this.dimension);
        } while (!this.availablePoint(point));
        return point;
    };
    Board.prototype.validPoint = function(point) {
        if (0 <= point.x && point.x < 20 && 0 <= point.y && point.y < 20) {
            return true;
        }
        return false;
    };
    Board.prototype.getAliveTanksInRandomOrder = function() {
        var result = [];
        // TODO: Add the random
        this.tanks.forEach(function(tank) {
            if (tank.isAlive()) {
                result.push(tank);
            }
        });
        return result;
    };
    Board.prototype.fire = function(tank) {
        var point = tank.point;
        for ( var i = 0; i < Tank.prototype.fireRange; i += 1) {
            point = point.withOffset(tank.direction);
            if (!this.validPoint(point)) {
                break;
            } else if (this.availablePoint(point)) {
                this.laser.set(i, point, tank.direction);
            } else {
                var otherTank = this.tankAt(point);
                var aliveBeforeHit = otherTank.isAlive();
                otherTank.hit(tank.direction.opposite());
                var aliveAfterHit = otherTank.isAlive();
                if (aliveAfterHit) {
                    tank.hits += 1;
                } else if (aliveBeforeHit && !aliveAfterHit) {
                    tank.frags += 1;
                }
                break;
            }
        }
    };
    Board.prototype.tankAt = function(point) {
        return this.tanks.filter(function(tank) {
            return tank.point.equals(point);
        })[0];
    };
    Board.prototype.moveReset = function() {
        this.laser.reset();
        this.tanks.forEach(function(tank) {
            tank.hitReset();
        });
    };

    function Laser(board) {
        var self = this;
        var laserPath = 'images/laser.png';
        this.laserImages = [];
        for ( var i = 0; i < Tank.prototype.fireRange; i += 1) {
            path2image(laserPath, function(image) {
                self.laserImages[i] = image;
                board.htmlDiv.appendChild(image);
                image.style.position = "absolute";
                image.style.zIndex = "2";
                image.style.display = "none";
            });
        }
    }
    Laser.prototype.set = function(i, point, direction) {
        var image = this.laserImages[i];
        image.style.transform = 'translate(' + point.x * 20 + 'px, ' + point.y * 20 + 'px) rotate('
                + direction.angle + 'deg)';
        image.style.display = 'block';
    };
    Laser.prototype.reset = function() {
        this.laserImages.forEach(function(image) {
            image.style.display = 'none';
        });
    };

    var tankImagePaths = [ 'images/tank_blue.png', 'images/tank_brown.png',
            'images/tank_coral.png', 'images/tank_cyan.png', 'images/tank_gold.png',
            'images/tank_green.png', 'images/tank_indigo.png', 'images/tank_red.png' ];

    function Tank(board, strategy) {
        var self = this;
        this.strategy = strategy;
        this.direction = Direction.prototype.random();
        this.point = board.randomPoint();
        this.oldPoint = {};
        this.hitDirection = {};
        this.energy = this.maxEnergy;
        this.frags = 0;
        this.hits = 0;

        this.imageId = tankImagePaths.shift();
        this.image = {};
        path2image(this.imageId, function(image) {
            image.style.position = "absolute";
            image.style.zIndex = "2";
            image.className = 'tank';
            board.htmlDiv.appendChild(image);
            self.image = image;
        });

        this.hitImage = {};
        path2image('images/hit.png', function(image) {
            image.style.position = "absolute";
            image.style.zIndex = "3";
            image.style.display = "none";
            board.htmlDiv.appendChild(image);
            self.hitImage = image;
        });

        this.rotateAngle = this.direction.angle;

        this.updateTransform();

        board.addTank(this);
    }
    Tank.prototype.fireRange = 10;
    Tank.prototype.maxEnergy = 5;
    Tank.prototype.updateTransform = function() {
        this.image.style.transform = "translate(" + this.point.x * 20 + "px, " + this.point.y * 20
                + "px) rotate(" + this.rotateAngle + "deg)";
        this.hitImage.style.transform = "translate(" + this.point.x * 20 + "px, " + this.point.y
                * 20 + "px)";
    };
    Tank.prototype.toString = function() {
        return this.imageId + ' @ x=' + this.point.x + ', y=' + this.point.y + ', '
                + this.direction.name + ', energy=' + this.energy;
    };
    Tank.prototype.move = function(board) {
        var move = this.strategy.getNextMove(new Input(this, board));
        switch (move.name) {
        case Forward.name:
            var newPoint = this.point.withOffset(this.direction);
            if (board.validPoint(newPoint) && board.availablePoint(newPoint)) {
                this.oldPoint = this.point;
                this.point = newPoint;
            } else {
                // blocked
            }
            break;
        case TurnLeft.name:
            this.direction = this.direction.counterClockwise();
            this.rotateAngle -= 90;
            break;
        case TurnRight.name:
            this.direction = this.direction.clockwise();
            this.rotateAngle += 90;
            break;
        case Fire.name:
            board.fire(this);
            break;
        case Wait.name:
            break;
        }
        this.updateTransform();
    };
    Tank.prototype.hit = function(direction) {
        if (0 < this.energy) {
            this.energy -= 1;
            this.hitImage.style.display = "block";
            if (this.energy === 0) {
                // frag
            } else {
                // hit
                // TODO: hitDirection
            }
        } else {
            // already dead
        }
    };
    Tank.prototype.hitReset = function() {
        if (this.isAlive()) {
            this.hitImage.style.display = "none";
        }
    };
    Tank.prototype.isAlive = function() {
        return 0 < this.energy;
    };

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

    function Dimension(width, height) {
        this.width = width;
        this.height = height;
    }

    function Move() {
    }

    function Fire() {
        this.name = 'Fire';
    }
    Fire.prototype = new Move();
    Fire.prototype.construction = Fire;

    function TurnLeft() {
        this.name = 'TurnLeft';
    }
    TurnLeft.prototype = new Move();
    TurnLeft.prototype.construction = TurnLeft;

    function Forward() {
        this.name = 'Forward';
    }
    Forward.prototype = new Move();
    Forward.prototype.construction = Forward;

    function TurnRight() {
        this.name = 'TurnRight';
    }
    TurnRight.prototype = new Move();
    TurnRight.prototype.construction = TurnRight;

    function Wait() {
        this.name = 'Wait';
    }
    Wait.prototype = new Move();
    Wait.prototype.construction = Wait;

    Move.prototype.fire = new Fire();
    Move.prototype.turnLeft = new TurnLeft();
    Move.prototype.forward = new Forward();
    Move.prototype.turnRight = new TurnRight();
    Move.prototype.wait = new Wait();
    Move.prototype.random = function() {
        switch (random(5)) {
        case 0:
            return Move.prototype.fire;
        case 1:
            return Move.prototype.turnLeft;
        case 2:
            return Move.prototype.forward;
        case 3:
            return Move.prototype.turnRight;
        case 4:
            return Move.prototype.wait;
        }
    };

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

    // Strategies /////////////////////

    function Strategy() {
    }

    function Slacker() {
        this.name = 'Eric idle';
        this.author = 'Martin';
        this.getNextMove = function() {
            return Move.prototype.wait;
        };

    }
    Slacker.prototype = new Strategy();
    Slacker.prototype.constructor = Slacker;

    function Spinner() {
        var shot = true;
        this.name = 'Spinner';
        this.author = 'Martin';
        this.getNextMove = function() {
            if (shot) {
                shot = false;
                return Move.prototype.turnRight;
            }
            shot = true;
            return Move.prototype.fire;
        };
    }
    Spinner.prototype = new Strategy();
    Spinner.prototype.constructor = Spinner;

    function Random() {
        this.name = 'Random';
        this.author = 'Martin';
        this.getNextMove = function() {
            return Move.prototype.random();
        };
    }
    Random.prototype = new Strategy();
    Random.prototype.constructor = Random;

    function Derp() {
        this.name = 'Derp';
        this.author = 'JMH__';
        var moves = [ Move.prototype.fire, Move.prototype.turnLeft, Move.prototype.forward ];
        var moveIndex = 0;
        this.getNextMove = function() {
            return moves[moveIndex++ % moves.length];
        };
    }
    Derp.prototype = new Strategy();
    Derp.prototype.constructor = Derp;

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

})();
