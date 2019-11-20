<template>
	<td class="cell" :class="cellClass" @mouseover="onMouseOver" @mouseout="onMouseOut" @click="strike(false)">
    <!-- {{x}}:{{y}} -->
    </td>
</template>

<script>
export default {
  props: ["x", "y"],
  data() {
    return {
      frozen: true,
      mark: 0,
      mouseOver: false,
      activePlayer: 0
    };
  },

  methods: {
    strike(force) {
      if ((!this.frozen && !this.isMarked) || force) {
        this.mark = this.activePlayer;
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
    Event.$on("activePlayerChanged", (activeId) => {
      this.activePlayer = activeId;
    })

    Event.$on("clearCells", () => {
      this.mark = 0;
      this.frozen = false;
    });

    Event.$on("markCell", (x, y) => {
      if (this.x === x && this.y === y) {
        this.strike(true);
      }
    });

    Event.$on("disableCells", () => {
      this.frozen = true;
    });

    Event.$on("enableCells", (activePlayerId) => {
      this.frozen = false;
      this.activePlayer = activePlayerId;
    });
  },
  computed: {
    cellClass: function() {
      return {
        markA:
          this.mark === 1 ||
          (this.mouseOver === true &&
            this.activePlayer === 1 &&
            !this.frozen &&
            !this.isMarked),
        markB:
          this.mark === 2 ||
          (this.mouseOver === true &&
            this.activePlayer === 2 &&
            !this.frozen &&
            !this.isMarked)
      };
    },
    isMarked: function() {
      return this.mark !== 0;
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