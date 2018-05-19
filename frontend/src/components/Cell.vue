<template>
	<td class="cell" :class="cellClass" @mouseover="onMouseOver" @mouseout="onMouseOut" @click="strike">
    {{x}}:{{y}}
    </td>
</template>

<script>
export default {
  props: ["x", "y"],
  data() {
    return {
      frozen: false,
      mark: "",
      mouseOver: false
    };
  },

  methods: {
    strike() {
      if (!this.frozen) {
        this.mark = this.$parent.activePlayer;
        this.frozen = true;
        Event.$emit("strike", this.x, this.y);
      }
    },
    onMouseOver() {
      this.mouseOver = true;
    },
    onMouseOut() {
      this.mouseOver = false;
    }
  },

  created() {
    Event.$on("clearCells", () => {
      this.mark = "";
      this.frozen = false;
    });

    Event.$on("freeze", () => (this.frozen = true));
  },
  computed: {
    cellClass: function() {
      return {
        markA:
          this.mark === "1" ||
          (this.mouseOver === true &&
            this.$parent.activePlayer === "1" &&
            !this.frozen),
        markB:
          this.mark === "2" ||
          (this.mouseOver === true &&
            this.$parent.activePlayer === "2" &&
            !this.frozen)
      };
    }
  }
};
</script>

<style>
.cell {
  position: relative;
  width: 50px;
  max-width: 50px;
  border: white solid 1px;
  background-color: darkgray;
}

.cell:after {
  content: "";
  display: block;
  padding-bottom: 100%;
}

.markA {
  background-color: rgb(0, 0, 236);
}

.markB {
  background-color: rgb(255, 0, 0);
}
</style>