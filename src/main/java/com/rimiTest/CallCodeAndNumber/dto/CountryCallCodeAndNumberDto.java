package com.rimiTest.CallCodeAndNumber.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountryCallCodeAndNumberDto {
    private String callCodeAndNumber;
    private String country;

    public static CountryCallCodeAndNumberDto getCountryCallCodeAndNumberDtoForTest( String testCountryCodeAndNumber) {
        CountryCallCodeAndNumberDto countryCallCodeAndNumberDto = new CountryCallCodeAndNumberDto();
        countryCallCodeAndNumberDto.setCallCodeAndNumber(testCountryCodeAndNumber);

        return countryCallCodeAndNumberDto;
    }
}
