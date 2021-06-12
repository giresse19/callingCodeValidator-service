<template>
  <v-container>
    <v-row>
      <v-col>
        <h3 style="font-weight: bold; color: cadetblue">
          Successfully found country: {{ data.country }}
        </h3>
      </v-col>
    </v-row>

  </v-container>
</template>

<script>
export default {
  data(){
    return{
       data : {},
    }
  },
  mounted() {
    this.data = this.$route.params.data;
  }
}
</script>

<style>

</style>