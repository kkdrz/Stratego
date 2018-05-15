<template>
	<td class="cell" @click="strike">
        <div class="content">
            {{mark}}
        </div>
    </td>
</template>

<script>
	export default {
		props: ['name'],
		data () {
			return {
 				frozen: false,
				mark: ''
			}	
		},

		methods: {
			strike () {
				if (! this.frozen) {
					// gets either X or O from the Grid component
					this.mark = this.$parent.activePlayer

					this.frozen = true
					
					// fires an event to notify the Grid component that a mark is placed
					Event.$emit('strike', this.name)
				}
			}
		},

		created () {
			Event.$on('clearCell', () => {
				this.mark = ''
				this.frozen = false
			})

			Event.$on('freeze', () => this.frozen = true)
		}
	}
</script>

<style>
.cell {
  position: relative;
  width: 100px;
  border: white solid 1px;
  
}

.cell:hover {
	background-color: #255255;
}

.cell:after {
  content: "";
  display: block;
  padding-bottom: 100%;
}

.content {
  position: absolute;
  text-align: center;
  font-size: 20px;
  font-family: 'Gochi Hand', sans-serif;
  width: 100%;
  height: 100%;
}
</style>