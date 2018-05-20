<template>

    <table class="board">
		  <tr v-for="i in Number(size)" :key="i">
		    <cell :x="i-1" :y="j-1" v-for="j in Number(size)" :key="j"></cell>
		  </tr>
    </table>

</template>

<script>
import Cell from "./Cell.vue";
import Game from "../js/Game.js";
import AI from "../api/api_config.js";

export default {
  props: {
    size: Number
  },
  data() {
    return {
      activePlayer: 2,
      players: [
        {},
        {
          id: 1,
          type: "Human",
          name: "Player 1",
          score: 0,
          settings: {
            algorithm: "MinMax",
            depth: 3
          }
        },
        {
          id: 2,
          type: "AI",
          name: "Player 2",
          score: 0,
          settings: {
            algorithm: "MinMax",
            depth: 3
          }
        }
      ],
      cells: [[]]
    };
  },
  watch: {
    size: function(newSize, oldSize) {
      console.log("new size: " + newSize)
      this.clearCells(newSize);
    }
  },
  methods: {
    clearCells: function(size) {
      Event.$emit("clearCells");
      this.cells = new Array(size);
      for (var i = 0; i < size; i++) {
        this.cells[i] = new Array(size);
        for (var j = 0; j < size; j++) {
          this.cells[i][j] = 0;
        }
      }
    },
    changePlayer() {
      this.activePlayer = this.activePlayer === 1 ? 2 : 1;
      Event.$emit("changeActivePlayer", this.activePlayer);
    },
    changeGameStatus(x, y) {
      var horizontally = Game.countPointsHorizontally(this.cells, x, y);
      var vertically = Game.countPointsVertically(this.cells, x, y);
      var diagonallyLT = Game.countPointsDiagonallyLeftToTop(this.cells, x, y);
      var diagonallyLB = Game.countPointsDiagonallyLeftToBottom(
        this.cells,
        x,
        y
      );

      var newPoints = horizontally + vertically + diagonallyLT + diagonallyLB;

      if (newPoints > 1) {
        this.players[this.activePlayer].score += newPoints;
        Event.$emit("playerDataChanged", this.players[this.activePlayer]);
      }

      if (!Game.isMovePossible(this.cells)) {
        console.log("move not possible");
        Event.$emit("moveIsImpossible");
      }
    },
    checkIfAINeeded() {
      var currentPlayer = this.activePlayer;
      if (this.players[currentPlayer].type === "AI") {
        Event.$emit("disableUser");
        var gameState = {
          currentPlayer: this.activePlayer,
          opponentPlayer: this.activePlayer === 1 ? 2 : 1,
          algorithm: this.players[this.activePlayer].settings.algorithm,
          depth: this.players[this.activePlayer].settings.depth,
          board: this.cells,
          cellSelector: "central",
          stateEvaluator: "symbols_in_completed_lines"
        };

        AI.nextMove(gameState).then(response => {
          if (this.players[currentPlayer].type === "AI") {
            Event.$emit("markCell", response[1], response[2]);
          } else {
            Event.$emit("enableUser");
          }
        });
      } else {
        Event.$emit("enableUser");
      }
    }
  },
  created() {
    this.clearCells(this.size);

     Event.$on("resetGame", (boardSize) => {
       this.clearCells(boardSize);
       this.players[1].score = 0
       this.players[2].score = 0
       Event.$emit("playerDataChanged", this.players[1])
       Event.$emit("playerDataChanged", this.players[2])
     });

    Event.$on("strike", (x, y) => {
      this.cells[x][y] = this.activePlayer;
      this.changeGameStatus(x, y);
      this.changePlayer();
      this.checkIfAINeeded();
    });

    Event.$on("settingsChanged", (playerID, settings) => {
      var oldType = this.players[playerID].type
      this.players[playerID].type = settings.type;
      this.players[playerID].settings.algorithm = settings.algorithm;
      this.players[playerID].settings.depth = settings.depth;

      if (playerID === this.activePlayer) {
        if (oldType !=="AI" && settings.type === "AI") {
          this.checkIfAINeeded();
        } else if (oldType !== "AI"){
          Event.$emit("enableUser");
        }
      }
    });

    this.changePlayer();
    this.checkIfAINeeded();
  },
  components: { Cell }
};
</script>

<style scoped>
.board {
  margin: 0 auto;
  background-color: #34495e;
  color: #fff;
  border-collapse: collapse;
}
</style>
