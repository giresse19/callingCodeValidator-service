<template>
  <v-container>
    <v-row no-gutters style="height: 1000px">

      <v-col
          cols="12"
          sm="12"
          md="12"
      >
        <div class="pa-2">
          <div class="right-body">
            <v-card
                height="40%"
                width="30%"
                background=""
            >
              <v-card-title>Searched results</v-card-title>
              <v-card-text>
               <span style="color: green">Successfully found country:</span> {{ data.country }}
              </v-card-text>

              <v-card-actions>
                <v-btn
                    class="btn-style"
                    @click="goBack">
                  Back
                </v-btn>
              </v-card-actions>
            </v-card>
          </div>
        </div>

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
  },
  methods:{
    goBack(){
      return this.$router.go(-1)
    }
  }
}
</script>

<style>

</style>