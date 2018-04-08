package robot

import org.w3c.dom.*
import kotlin.browser.document

/*
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
 */
class Score {
    private val htmlDiv = document.getElementById("scoreDiv")
    private val table = document.createElement("table")
    private val headers = mutableListOf("Image", "Name", "Author", "Energy", "Hits", "Frags")
    private val headerLength = headers.size
    private val roundText = document.createTextNode("Round: ")
    private val roundNo = document.createTextNode("0")

    constructor(players: Int) {
        for (i in 0 .. players) {
            val tr = document.createElement("tr")
            for (j in 0 .. headerLength) {
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
        htmlDiv!!.appendChild(table)
        htmlDiv.appendChild(roundText)
        htmlDiv.appendChild(roundNo)
    }

    fun update(round: Int, tanks: List<Tank>) {
        val trNodes = htmlDiv!!.childNodes[0]!!.childNodes
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
        htmlDiv.childNodes[2].asDynamic().data = round
    }
}


