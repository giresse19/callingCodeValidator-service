package com.rimiTest.CallCodeAndNumber.persistence.model.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CountryCallCodeAndNumberRequest {

    @NotBlank(message = "call code and number can't be empty!")
    private String callCodeAndNumber;
}
