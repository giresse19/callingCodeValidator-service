package com.rimiTest.CallCodeAndNumber.service;

import com.rimiTest.CallCodeAndNumber.persistence.model.request.PhoneNumberRequest;
import com.rimiTest.CallCodeAndNumber.persistence.model.response.PhoneNumberResponse;

public interface PhoneNumberService {
  PhoneNumberResponse getCountryByPhoneNumber(PhoneNumberRequest phoneNumberRequest);

}
