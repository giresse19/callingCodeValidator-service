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
public class CountryCallCodeAndNumberIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetCountryByCountryCallCodeAndNumberIsInvalid() throws Exception {
        mockMvc.perform(
                post("/api/countryPrefixAndNumber").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"callCodeAndNumber\":\"76767\"}"))
                .andExpect(status().isBadRequest()).andExpect(jsonPath("message")
                .value(ErrorMessages.INVALID_INPUT.getErrorMessage()));
    }

    @Test
    public void testGetCountryByCountryCallCodeAndNumberIsValid() throws Exception {
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
                .content("{\"callCodeAndNumber\":\"1 (310) 703-6671\"}")).andExpect(status().isOk())
                .andExpect(jsonPath("country").value("United States of America, Canada"));

        mockMvc.perform(post("/api/countryPrefixAndNumber").contentType(MediaType.APPLICATION_JSON)
                .content("{\"callCodeAndNumber\":\"+1 (340) 703-6671\"}")).andExpect(status().isOk())
                .andExpect(jsonPath("country").value("United States Virgin Islands"));
    }

}
