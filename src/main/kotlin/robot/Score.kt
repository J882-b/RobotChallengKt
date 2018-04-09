package robot

import org.w3c.dom.*
import kotlin.browser.document

/*
    function Score(players) {
        this.scoreDiv = document.getElementById('scoreDiv');
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
        this.scoreDiv.appendChild(table);
        var roundText = document.createTextNode('Round: ');
        var roundNo = document.createTextNode('0');
        this.scoreDiv.appendChild(roundText);
        this.scoreDiv.appendChild(roundNo);

    }
    Score.prototype.update = function(round, tanks) {
        var trNodes = this.scoreDiv.childNodes[0].childNodes;
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
        this.scoreDiv.childNodes[2].data = round;
    };
 */
class Score {
    private val scoreDiv = document.getElementById("scoreDiv") as HTMLDivElement
    private val table = document.createElement("table") as HTMLTableElement
    private val headers = mutableListOf("Image", "Name", "Author", "Energy", "Hits", "Frags")
    private val headerLength = headers.size
    private val roundText = document.createTextNode("Round: ")
    private val roundNo = document.createTextNode("0")

    constructor(players: Int) {
        console.log("Score constructor()")
        for (i in 0..players) {
            val tr = document.createElement("tr")
            for (j in 0..(headerLength - 1)) {
                var cell : Node
                var tag = "td"
                if (i == 0) {
                    tag = "th"
                }
                val tx = document.createElement(tag)
                if (j == 0 && i != 0) {
                    cell = document.createElement("img")
                } else {
                    var text = "."
                    if (i == 0) {
                        text = headers.shift()!!
                    }
                    cell = document.createTextNode(text)
                }
                tx.appendChild(cell)
                tr.appendChild(tx)
            }
            table.appendChild(tr)
        }
        table.asDynamic().border = "1"
        scoreDiv.appendChild(table)
        scoreDiv.appendChild(roundText)
        scoreDiv.appendChild(roundNo)
    }

    fun update(round: Int, tanks: List<Tank>) {
        console.log("Score update")
        val trNodes = scoreDiv.childNodes[0]!!.childNodes
        var row = 1
        tanks.forEach { tank ->
            if (round == 1) {
                trNodes[row]!!.childNodes[0]!!.childNodes[0].asDynamic().src = tank.imageId
            }
            trNodes[row]!!.childNodes[1]!!.childNodes[0].asDynamic().data = tank.strategy.name
            trNodes[row]!!.childNodes[2]!!.childNodes[0].asDynamic().data = tank.strategy.author
            trNodes[row]!!.childNodes[3]!!.childNodes[0].asDynamic().data = tank.energy
            trNodes[row]!!.childNodes[4]!!.childNodes[0].asDynamic().data = tank.hits
            trNodes[row]!!.childNodes[5]!!.childNodes[0].asDynamic().data = tank.frags
            row += 1
        }
        scoreDiv.childNodes[2].asDynamic().data = round
    }
}


