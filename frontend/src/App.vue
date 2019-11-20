<template>
  <b-container fluid>
    <b-row class="mb-1">
      <b-col cols="12"><info-bar :disabled="!this.humanInput" :play="!gameRunning" :score1="players[1].score" :score2="players[2].score"></info-bar></b-col>
    </b-row>
    <b-row>
      <b-col cols="3"><settings :disabled="!this.humanInput" :heading="players[1].name" :playerID="1"></settings></b-col>
      <b-col cols="6">
        <board :size="boardSize"></board>
      </b-col>
      <b-col cols="3"><settings :disabled="!this.humanInput" :heading="players[2].name" :playerID="2"></settings></b-col>
    </b-row>
    <b-modal hide-footer hide-header v-model="endGameModal">
      <b-container fluid>
        <b-row class="mb-1">
          <b-col class="text-center" cols="12"> <h1>{{modalMessage}}</h1></b-col>
        </b-row>
        <b-row class="mb-1">
          <b-col class="text-center" cols="12"> <h3>{{players[1].score}}:{{players[2].score}}</h3></b-col>
        </b-row>
        <b-row class="mb-1">
          <b-col offset="10" cols="2"><b-button size="lg" @click="resetGame()">OK</b-button></b-col>
        </b-row>
      </b-container>
    </b-modal>
  </b-container>
</template>

<script>
import Board from "./components/Board.vue";
import Settings from "./components/Settings.vue";
import InfoBar from "./components/InfoBar.vue";

import Game from "./js/Game.js";
import AI from "./api/api_config.js";

export default {
  name: "app",
  data() {
    return {
      boardSize: 10,
      cells: [[]],
      gameRunning: false,
      activePlayerNumber: 1,
      humanInput: true,
      players: [
        {},
        {
          id: 1,
          type: "Human",
          name: "Player 1",
          score: 0,
          settings: {
            algorithm: "MinMax",
            depth: 3,
            evaluator: "",
            selector: ""
          }
        },
        {
          id: 2,
          type: "AI",
          name: "Player 2",
          score: 0,
          settings: {
            algorithm: "MinMax",
            depth: 3,
            evaluator: "",
            selector: ""
          }
        }
      ],
      modalMessage: "",
      endGameModal: false
    };
  },
  created() {
    Event.$on("strike", (x, y) => {
      this.cells[x][y] = this.activePlayerNumber;
      this.checkPoints(x, y);
      this.changePlayer();
      this.startGame();
    });

    Event.$on("gridSizeChanged", boardSize => {
      this.boardSize = Number(boardSize);
      this.resetGame();
    });

    Event.$on("startGame", () => {
      this.startGame();
    });

    Event.$on("resetGame", () => {
      this.resetGame();
    });

    Event.$on("moveIsImpossible", () => {
      this.showEndModal();
    });

    Event.$on("settingsChanged", (playerID, settings) => {
      this.players[playerID].type = settings.type;
      this.players[playerID].settings.algorithm = settings.algorithm;
      this.players[playerID].settings.depth = settings.depth;
      this.players[playerID].settings.evaluator = settings.evaluator;
      this.players[playerID].settings.selector = settings.selector;
    });

    this.clearCells(this.boardSize);
  },
  computed: {
    activePlayer: function() {
      return this.players[this.activePlayerNumber];
    },
    gameState: function() {
      return {
        currentPlayer: this.activePlayerNumber,
        opponentPlayer: this.activePlayerNumber === 1 ? 2 : 1,
        algorithm: this.activePlayer.settings.algorithm,
        depth: this.activePlayer.settings.depth,
        board: this.cells,
        cellSelector: this.activePlayer.settings.selector,
        stateEvaluator: this.activePlayer.settings.evaluator
      };
    }
  },
  methods: {
    startGame() {
      this.gameRunning = true;
      this.checkIfMoveIsPossible();
      Event.$emit("activePlayerChanged", this.activePlayerNumber);
      this.roundStart();
    },
    checkIfMoveIsPossible() {
      if (!Game.isMovePossible(this.cells)) {
        this.gameRunning = false;
        this.showEndModal();
      }
    },
    resetGame() {
      this.gameRunning = false;
      this.clearCells(this.boardSize);
      this.resetPlayersScores();
      this.resetModal();
      Event.$emit("activePlayerChanged", 0);
      this.turnOffHumanBoardInput();
      this.turnOnHumanSettingsInput();
    },
    checkPoints(x, y) {
      var points = Game.countPoints(this.cells, x, y);
      if (points > 1) {
        this.players[this.activePlayerNumber].score += points;
      }
    },
    roundStart() {
      if (this.activePlayer.type === "AI") {
        this.turnOffHumanSettingsInput();
        AI.nextMove(this.gameState).then(response => {
          if (this.activePlayer.type === "AI" && this.gameRunning) {
            Event.$emit("markCell", response[1], response[2]);
          }
        });
      } else if (this.activePlayer.type === "Human") {
        this.turnOnHumanSettingsInput();
      }
    },
    turnOffHumanBoardInput() {
      Event.$emit("disableCells");
    },
    turnOnHumanBoardInput() {
      Event.$emit("enableCells", this.activePlayerNumber);
    },
    turnOffHumanSettingsInput() {
      this.turnOffHumanBoardInput();
      this.humanInput = false;
    },
    turnOnHumanSettingsInput() {
      this.turnOnHumanBoardInput();
      this.humanInput = true;
    },
    resetPlayersScores() {
      this.players[1].score = 0;
      this.players[2].score = 0;
    },
    resetModal() {
      this.modalMessage = "";
      this.endGameModal = false;
    },
    showEndModal() {
      var message = "";
      if (this.players[1].score > this.players[2].score) {
        message = this.players[1].name + " won the match!";
      } else if (this.players[2].score > this.players[1].score) {
        message = this.players[2].name + " won the match!";
      } else if (this.players[2].score === this.players[1].score) {
        message = "Draw. There is no winner!";
      } else {
        message = "Something bad happened :(";
      }

      this.modalMessage = message;
      this.endGameModal = true;
    },
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
      this.activePlayerNumber = this.activePlayerNumber === 1 ? 2 : 1;
      Event.$emit("activePlayerChanged", this.activePlayer.id);
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
