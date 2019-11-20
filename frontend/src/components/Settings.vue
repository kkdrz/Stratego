<template>
  <b-card :bg-variant="this.color" text-variant="white">
    <b-container fluid>
      <b-row text-center class="mb-2">
        <b-col>
          <h3>{{heading}}</h3>
        </b-col>
      </b-row>
      <b-row text-center>
        <div class="col-12">
          <b-input-group prepend="Player" class="mb-2">
            <b-form-select v-model="player" placeholder="Player type" :options="players" :disabled="this.disabled"></b-form-select>
          </b-input-group>
        </div>
      </b-row>
      <b-row text-center>
        <div class="col-12">
          <b-input-group v-if="player === 'AI'" prepend="Algorithm" class="mb-2">
            <b-form-select v-model="selectedAlgorithm" :options="algorithms" :disabled="this.disabled"/>
          </b-input-group>
        </div>
      </b-row>
      <b-row text-center>
        <div class="col-12">
          <b-input-group v-if="player === 'AI'" prepend="Depth" class="mb-2">
            <b-form-select v-model="selectedDepth" :options="depths" :disabled="this.disabled"/>
          </b-input-group>
        </div>
      </b-row>
      <b-row text-center>
        <div class="col-12">
          <b-input-group v-if="player === 'AI'" prepend="Evaluator" class="mb-2">
            <b-form-select v-model="selectedEvaluator" :options="evaluators" :disabled="this.disabled"/>
          </b-input-group>
        </div>
      </b-row>
      <b-row text-center>
        <div class="col-12">
          <b-input-group v-if="player === 'AI'" prepend="CellSelector" class="mb-2">
            <b-form-select v-model="selectedCellSelector" :options="cellSelectors" :disabled="this.disabled" />
          </b-input-group>
        </div>
      </b-row>
    </b-container>
  </b-card>
</template>

<script>
export default {
  props: ["heading", "playerID", "disabled"],
  data() {
    return {
      player: "Human",
      players: ["AI", "Human"],
      selectedAlgorithm: "AlphaBeta",
      algorithms: ["MinMax", "AlphaBeta"],
      selectedDepth: 3,
      depths: [1, 2, 3, 4, 5, 6, 7],
      selectedEvaluator: "Symbols in completed lines",
      evaluators: ["Symbols in completed lines", "Cluster of symbols"],
      selectedCellSelector: "Central",
      cellSelectors: ["Central", "Border"],
      color: "dark"
    };
  },
  created() {
    Event.$on("activePlayerChanged", playerID => {
      if (Number(playerID) === Number(this.playerID)) {
        this.color = "info";
      } else {
        this.color = "dark";
      }
    });
    this.settingsChanged();
  },
  watch: {
    selectedAlgorithm: function(after, before) {
      this.settingsChanged();
    },
    selectedDepth: function(after, before) {
      this.settingsChanged();
    },
    player: function(after, before) {
      this.settingsChanged();
    },
    selectedEvaluator: function(after, before) {
      this.settingsChanged();
    },
    selectedCellSelector: function(after, before) {
      this.settingsChanged();
    }
  },
  methods: {
    settingsChanged() {
      Event.$emit("settingsChanged", this.playerID, {
        type: this.player,
        algorithm: this.selectedAlgorithm,
        depth: this.selectedDepth,
        evaluator: this.selectedEvaluator,
        selector: this.selectedCellSelector
      });
    }
  }
};
</script>

<style scoped>
.settings {
  background-color: aquamarine;
  padding: 10px;
}
</style>
