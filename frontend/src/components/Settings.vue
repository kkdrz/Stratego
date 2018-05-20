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
            <b-form-select v-model="player" placeholder="Player type" :options="players"></b-form-select>
          </b-input-group>
        </div>
      </b-row>
      <b-row text-center>
        <div class="col-12">
          <b-input-group v-if="player === 'AI'" prepend="Algorithm" class="mb-2">
            <b-form-select v-model="selectedAlgorithm" :options="algorithms" />
          </b-input-group>
        </div>
      </b-row>
      <b-row text-center>
        <div class="col-12">
          <b-input-group v-if="player === 'AI'" prepend="Depth" class="mb-2">
            <b-form-select v-model="selectedDepth" :options="depths" />
          </b-input-group>
        </div>
      </b-row>
    </b-container>
  </b-card>
</template>

<script>
export default {
  props: ["heading", "playerID"],
  data() {
    return {
      player: "Human",
      players: ["AI", "Human"],
      selectedAlgorithm: "MinMax",
      algorithms: ["MinMax", "AlphaBeta"],
      selectedDepth: "3",
      depths: ["1", "2", "3", "4", "5", "6", "7"],
      color: "dark"
    };
  },
  created() {
    Event.$on("changeActivePlayer", playerID => {
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
    }
  },
  methods: {
    settingsChanged() {
      Event.$emit("settingsChanged", this.playerID, {
        type: this.player,
        algorithm: this.selectedAlgorithm,
        depth: this.selectedDepth
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
