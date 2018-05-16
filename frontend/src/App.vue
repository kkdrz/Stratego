<template>

  <div id="app">
    <settings></settings>
    <board :size="boardSize"></board>
    
  </div>
</template>

<script>
import Board from "./components/Board.vue";
import Settings from "./components/Settings.vue";

export default {
  name: "app",
  data() {
    return {
      boardSize: 10,
      player1Score: 0,
      player2Score: 0
    };
  },
  created() {
    Event.$on("gridSizeChanged", boardSize => {
      this.boardSize = Number(boardSize);
    });
  },
  components: { Board, Settings }
};
</script>

<style>
#app {
  margin: 0 auto;
  margin-top: 10px;
  width: 70%;
  color: #34495e;
}
</style>
