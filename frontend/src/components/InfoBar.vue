<template>

<b-card  bg-variant="dark" text-variant="white">
  <b-container fluid>
    <b-row>
      <b-col cols="2" align-self="center">
          <b-input-group  prepend="Grid size" class="mb-2">
            <b-form-input type="number" v-model="gridSize" @change="onGridSizeChanged()"/>
          </b-input-group>
      </b-col>
      <b-col class="text-center" cols="8" align-self="center"><h2>{{score1}}:{{score2}}</h2></b-col>
      <b-col v-if="!play" offset="1" cols="1" align-self="center">
            <b-button @click="resetGame" size="lg" variant="secondary">
                RESET
            </b-button>
      </b-col>
      <b-col v-if="play" offset="1" cols="1" align-self="center">
            <b-button @click="startGame" size="lg" variant="success">
                START
            </b-button>
      </b-col>
    </b-row>
  </b-container>
</b-card>

  
</template>

<script>
export default {
  props: ["score1", "score2", "play"],
  data() {
    return {
      gridSize: 10
    };
  },
  methods: {
    onGridSizeChanged: function() {
      Event.$emit("gridSizeChanged", this.gridSize);
    },
    startGame() {
      Event.$emit("startGame");
    },
    resetGame() {
      Event.$emit("resetGame");
    }
  }
};
</script>

<style scoped>
.infobar {
  background-color: aqua;
  margin-top: 5px;
  padding: 5px;
}
</style>
