<template>
  <b-container fluid>
    <b-row class="mb-1">
      <b-col cols="12"><info-bar :score1="player1Score" :score2="player2Score"></info-bar></b-col>
    </b-row>
    <b-row>
      <b-col cols="3"><settings :heading="player1Name" :playerNumber="1" :settings="player1Settings"></settings></b-col>
      <b-col cols="6">
        <board :size="boardSize"></board>
      </b-col>
      <b-col cols="3"><settings :heading="player2Name" :playerNumber="2" :settings="player2Settings"></settings></b-col>
      
    </b-row>
  </b-container>
</template>

<script>
  import Board from "./components/Board.vue";
  import Settings from "./components/Settings.vue";
  import InfoBar from './components/InfoBar.vue'

  export default {
    name: "app",
    data() {
      return {
        boardSize: 10,
        player1Score: 0,
        player2Score: 0,
        player1Settings: [],
        player2Settings: [],
        player1Name: "Player 1",
        player2Name: "Player 2"
      };
    },
    created() {
      Event.$on("gridSizeChanged", boardSize => {
        this.boardSize = Number(boardSize);
        this.player1Score = 0;
        this.player2Score = 0;
        Event.$emit('changeActivePlayer', 1)
      });

      Event.$on("increaseScore", (player, score) => {
        if(player === '1') {
          this.player1Score += score;
        } else if (player === '2') {
          this.player2Score += score;
        }
      });
    },
    methods: {
      
    },
    components: {
      Board,
      Settings,
      InfoBar
    }
  };

</script>

<style>

</style>
