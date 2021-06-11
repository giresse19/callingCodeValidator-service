package com.rimiTest.CallCodeAndNumber.service;

import com.rimiTest.CallCodeAndNumber.dto.CountryCallCodeAndNumberDto;

public interface CountryCallCodeAndNumberService {
  CountryCallCodeAndNumberDto getCountryByCountryCallCodeAndNumber(CountryCallCodeAndNumberDto countryCallCodeAndNumberDto);
  boolean isValidCountryCodeAndNumber(String countryCallCodeAndNumber);
    
}
