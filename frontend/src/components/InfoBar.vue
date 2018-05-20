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
    </b-row>
  </b-container>
</b-card>

  
</template>

<script>
  export default {
    props: ["score1", "score2"],
    data() {
      return {
          gridSize: 10
      };
    },
    methods: {
      onGridSizeChanged: function () {
        Event.$emit("resetGame", this.gridSize);
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
