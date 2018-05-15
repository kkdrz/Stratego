<template>

    <table class="board">
		  <tr v-for="i in Number(size)" :key="i">
		    <cell v-for="j in Number(size)" :key="j"></cell>
		  </tr>
    </table>

</template>

<script>
import Cell from './Cell.vue'
export default {
  props: {
      size: Number
  },
  components: { Cell }
}
</script>

<style scoped>
.board {
    margin: 0 auto;
	background-color: #34495e;
	color: #fff;
    border-collapse: collapse;
}
</style>
