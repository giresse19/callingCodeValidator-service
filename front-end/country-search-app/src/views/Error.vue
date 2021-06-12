<template>
  <v-container>
    <v-row>
      <v-col>
        <h1 style="font-weight: bold; color: cadetblue">
          Thanks for checking in, We were unable to get the country, Please try later!
        </h1>
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