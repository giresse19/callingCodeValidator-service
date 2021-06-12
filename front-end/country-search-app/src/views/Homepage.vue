<template>
  <v-container>
    <v-row no-gutters style="height: 1000px">

      <v-col
          cols="12"
          sm="12"
          md="12"
      >
        <div class="pa-12">
          <div class="right-body">
            <v-card
                height="40%"
                width="30%"
                background=""
            >
              <v-card-title>Enter country call-code and phone number!</v-card-title>
              <v-card-text>
                <v-form v-model="isValid" @submit.prevent="postFormData">
                  <v-text-field
                      label="Phone number. e.g: +(372)23120930"
                      v-model="callCodeAndNumber"
                      required
                      :rules="[
                        () => !!callCodeAndNumber || 'Phone number can not be empty.',
                        () => /(\d{11,31})/.test(callCodeAndNumber) || 'Number of digits must be between 11 and 31',
                    ]"
                  >
                  </v-text-field>
                </v-form>
              </v-card-text>

              <v-card-actions>
                <v-btn
                    class="btn-style"
                    :disabled="!isValid"
                    @click="postFormData">
                  Search
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
  name: 'Homepage',
  props: {
    msg: String
  },
  data() {
    return {
      callCodeAndNumber: '',
      isValid: true
    }

  },
  methods: {
    async postFormData() {
      const formData = {
        'callCodeAndNumber': this.callCodeAndNumber,
      };

      const response = await fetch(`${config.BASE_URL}/${config.PHONE_NUMBER}`, config.fetchOptionsPost(JSON.stringify(formData)));

      if (response.ok) {
        const data = await response.json()
        console.log(data);

        await this.$router.push({
          name: 'success',
          params: {data}
        });

      } else {
        const data = await response.json()
        console.log(data);

        await this.$router.push({
          name: 'error',
          params: {data}
        });
      }
    }
  }
}
</script>

<style>
.right-body {
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 41px;
  padding: 41px;
}
</style>
