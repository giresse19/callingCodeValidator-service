package com.rimiTest.CallCodeAndNumber.service;

import com.rimiTest.CallCodeAndNumber.persistence.model.request.CountryCallCodeAndNumberRequest;
import com.rimiTest.CallCodeAndNumber.persistence.model.response.CountryCallCodeAndNumberResponse;

// todo: change names to possibly countryCodeService etc
public interface CountryCallCodeAndNumberService {
  CountryCallCodeAndNumberResponse getCountryByPhoneNumber(CountryCallCodeAndNumberRequest countryCallCodeAndNumberRequest);

}
