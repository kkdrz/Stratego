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
      activePlayer: "O",
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
    },
    changeGameStatus() {}
  },
  created() {
    this.clearCells(this.size);

    Event.$on("strike", (x, y) => {
      this.cells[x][y] = this.activePlayer === "O" ? 1 : 2;
      this.gameStatus = this.changeGameStatus();
      this.changePlayer();
    });
  },
  computed: {
    nonActivePlayer() {
      if (this.activePlayer === "O") {
        return "X";
      }
      return "O";
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
