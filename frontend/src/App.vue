<template>
  <b-container fluid>
    <b-row class="mb-1">
      <b-col cols="12"><info-bar :score1="player1Score" :score2="player2Score"></info-bar></b-col>
    </b-row>
    <b-row>
      <b-col cols="3"><settings :heading="player1Name" :playerID="1"></settings></b-col>
      <b-col cols="6">
        <board :size="boardSize"></board>
      </b-col>
      <b-col cols="3"><settings :heading="player2Name" :playerID="2"></settings></b-col>
    </b-row>
    <b-modal hide-footer hide-header v-model="endGameModal">
      <b-container fluid>
        <b-row class="mb-1">
          <b-col class="text-center" cols="12"> <h1>{{modalMessage}}</h1></b-col>
        </b-row>
        <b-row class="mb-1">
          <b-col class="text-center" cols="12"> <h3>{{player1Score}}:{{player2Score}}</h3></b-col>
        </b-row>
        <b-row class="mb-1">
          <b-col offset="10" cols="2"><b-button size="lg" @click="resetGame">OK</b-button></b-col>
        </b-row>
      </b-container>
    </b-modal>
  </b-container>
</template>

<script>
import Board from "./components/Board.vue";
import Settings from "./components/Settings.vue";
import InfoBar from "./components/InfoBar.vue";

export default {
  name: "app",
  data() {
    return {
      boardSize: 10,
      player1Score: 0,
      player2Score: 0,
      player1Name: "Player 1",
      player2Name: "Player 2",
      endGameModal: false,
      modalMessage: ""
    };
  },
  created() {
    Event.$on("playerDataChanged", player => {
      if (player.id === 1) {
        this.player1Score = player.score;
        this.player1Name = player.name;
      } else if (player.id === 2) {
        this.player2Score = player.score;
        this.player2Name = player.name;
      }
    });

    Event.$on("resetGame", boardSize => {
      console.log("bsize: " + boardSize)
      this.boardSize = Number(boardSize);
      this.player1Score = 0;
      this.player2Score = 0;
      this.modalMessage = "";
      this.endGameModal = false;
      Event.$emit("changeActivePlayer", 1);
    });

    Event.$on("moveIsImpossible", () => {
      var message = "";
      if (this.player1Score > this.player2Score) {
        message = this.player1Name + " won the match!";
      } else if (this.player2Score > this.player1Score) {
        message = this.player2Name + " won the match!";
      } else if (this.player2Score === this.player1Score) {
        message = "Draw. There is no winner!";
      } else {
        message = "Something bad happened :(";
      }

      this.modalMessage = message;
      this.endGameModal = true;
    });
  },
  methods: {
    resetGame() {
      Event.$emit("resetGame", this.boardSize)
    }
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
