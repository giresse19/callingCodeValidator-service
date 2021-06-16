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
                <span style="color: red">Error, {{ message }}</span>

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
import config from "@/config/config";

export default {
  data() {
    return {
      response: {},
      message: "",
    }
  },
  mounted() {
    this.response = this.$route.params.response;
    this.getMessage();
  },
  methods: {
    goBack() {
      return this.$router.go(-1)
    },
    async getMessage() {
      this.response = this.$route.params.response;

      if (this.response.status === 404) {
        this.message = config.NOT_FOUND_MESSAGE
      } else {
        const data = await this.response.json();
        this.message = data.message
      }
    }
  },

}
</script>