<template>

    <table class="board">
		  <tr v-for="i in Number(size)" :key="i">
		    <cell :x="i-1" :y="j-1" v-for="j in Number(size)" :key="j"></cell>
		  </tr>
    </table>

</template>

<script>
import Cell from "./Cell.vue";
export default {
  props: {
    size: Number
  },
  data() {
    return {
      activePlayer: "2",
      gameStatus: "turn",
      cells: [[]]
    };
  },
  watch: {
    size: function(newSize, oldSize) {
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
      this.activePlayer = this.nonActivePlayer;
      Event.$emit("changeActivePlayer", this.activePlayer);
    },
    changeGameStatus(x, y) {
      console.log(this.countPointsHorizontally(x, y));
      this.countPointsVertically(x, y);
      this.countPointsDiagonally(x, y);
    },
    countPointsHorizontally(x, y) {
      var player = this.cells[x][y];
      var lineClosed = true;
      for (var i = 0; i < this.cells[x].length; i++) {
        if (this.cells[x][i] === 0) {
          lineClosed = false;
        }
      }

      var points = 1;
      if (lineClosed) {
        for (var i = y + 1; i < this.cells[x].length; i++) {
          if (this.cells[x][i] === player) {
            points++;
          } else {
            break;
          }
        }

        for (var i = y - 1; i >= 0; i--) {
          if (this.cells[x][i] === player) {
            points++;
          } else {
            break;
          }
        }
      }
      return points >= 2 ? points : 0;
    },
    countPointsVertically() {},
    countPointsDiagonally() {}
  },
  created() {
    this.clearCells(this.size);

    Event.$on("strike", (x, y) => {
      this.cells[x][y] = this.activePlayer === "1" ? 1 : 2;
      this.gameStatus = this.changeGameStatus(x, y);
      this.changePlayer();
    });

    this.changePlayer();
  },
  computed: {
    nonActivePlayer() {
      if (this.activePlayer === "1") {
        return "2";
      }
      return "1";
    }
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
