package robot

import kotlin.browser.window

/*
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
 */
class Game {
    private val board = Board()
    private val strategies = listOf(Spinner(), Derp(), Random(), Random(), Random(), Random(), Random(), FireFire())
    private val score: Score
    private var round = 0
    private var nextToMoveQueue = mutableListOf<Tank>()

    constructor() {
        for (strategy in strategies) {
            Tank(board, strategy)
        }
        score = Score(board.tanks.size)
    }

    fun start() {
        console.log("start the game")
        nextMove()
    }

    private fun nextMove() {
        var winner = false

        if (nextToMoveQueue.size == 0) {
            round += 1
            nextToMoveQueue.addAll(board.getAliveTanksInRandomOrder())
            if (nextToMoveQueue.size == 1) {
                winner = true
            }
        }
        score.update(round, board.tanks)
        board.moveReset()
        if (!winner) {
            var tank = nextToMoveQueue.shift()

            if (tank.isAlive()) {
                tank.move(board)
            }
            window.setTimeout({nextToMoveQueue}, 200)
        } else {
            val alertMessage = "The winner is ${nextToMoveQueue[0].strategy.name}"
            window.alert(alertMessage)
        }
    }
}
