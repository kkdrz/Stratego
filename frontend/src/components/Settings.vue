<template>
<div class="settings container">
  
  <div class="row">
    <div class="col-2">
        <b-form-input type="number" v-model="gridSize" @change="onGridSizeChanged()" class="mb-1"/>
    </div>
    <div class="col-2">
      <b-form-select v-model="playerOne" :options="players" class="mb-1"/>
    </div>
    <div class="col-2">
      <b-form-select v-if="playerOne === 'AI'" v-model="p1_selectedAlgorithm" :options="algorithms" class="mb-1"/>
    </div>
    <div class="col-2">
      <b-form-select v-if="playerOne === 'AI'" v-model="p1_selectedDepth" :options="depths" class="mb-1"/>
    </div>
  </div>
  <div class="row">
    <div class="col-2">
        
    </div>
    <div class="col-2">
      <b-form-select v-model="playerTwo" :options="players"/>
    </div>
    <div class="col-2">
      <b-form-select v-if="playerTwo === 'AI'" v-model="p2_selectedAlgorithm" :options="algorithms" />
    </div>
    <div class="col-2">
      <b-form-select v-if="playerTwo === 'AI'" v-model="p2_selectedDepth" :options="depths" />
    </div>
  </div>
</div>
  
</template>

<script>
export default {
  data() {
    return {
      gridSize: 10,
      playerOne: "Human",
      playerTwo: "AI",
      players: ["AI", "Human"],
      p1_selectedAlgorithm: "MinMax",
      p2_selectedAlgorithm: "MinMax",
      algorithms: ["MinMax", "AlphaBeta"],
      p1_selectedDepth: "3",
      p2_selectedDepth: "3",
      depths: ["1", "2", "3", "4", "5", "6", "7"]
    };
  },
  methods: {
    onGridSizeChanged: function() {
      Event.$emit("gridSizeChanged", this.gridSize);
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
