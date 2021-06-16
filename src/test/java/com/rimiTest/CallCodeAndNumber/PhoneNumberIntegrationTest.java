package com.rimiTest.CallCodeAndNumber;

import com.rimiTest.CallCodeAndNumber.persistence.model.ErrorMessages;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PhoneNumberIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getCountryByPhoneNumber_shouldReturnErrorMessage_whenPhoneNumberIsInvalid() throws Exception {
        mockMvc.perform(
                post("/api/countryPrefixAndNumber").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"callCodeAndNumber\":\"76767\"}"))
                .andExpect(status().isBadRequest()).andExpect(jsonPath("message")
                .value(ErrorMessages.INVALID_INPUT.getErrorMessage()));
    }

    @Test
    public void getCountryByPhoneNumber_shouldReturnCountry_whenPhoneNumberIsValid() throws Exception {
        mockMvc.perform(post("/api/countryPrefixAndNumber").contentType(MediaType.APPLICATION_JSON)
                .content("{\"callCodeAndNumber\":\"+37259620860\"}")).andExpect(status().isOk())
                .andExpect(jsonPath("country").value("Estonia"));

        mockMvc.perform(post("/api/countryPrefixAndNumber").contentType(MediaType.APPLICATION_JSON)
                .content("{\"callCodeAndNumber\":\"+237670126254\"}")).andExpect(status().isOk())
                .andExpect(jsonPath("country").value("Cameroon"));

        mockMvc.perform(post("/api/countryPrefixAndNumber").contentType(MediaType.APPLICATION_JSON)
                .content("{\"callCodeAndNumber\":\"0023770126254\"}")).andExpect(status().isOk())
                .andExpect(jsonPath("country").value("Cameroon"));

        mockMvc.perform(post("/api/countryPrefixAndNumber").contentType(MediaType.APPLICATION_JSON)
                .content("{\"callCodeAndNumber\":\"+2132612 300 0\"}")).andExpect(status().isOk())
                .andExpect(jsonPath("country").value("Algeria"));

        mockMvc.perform(post("/api/countryPrefixAndNumber").contentType(MediaType.APPLICATION_JSON)
                .content("{\"callCodeAndNumber\":\"893-6671676767\"}")).andExpect(status().isNotFound())
                .andExpect(jsonPath("country").doesNotExist());
    }

}
