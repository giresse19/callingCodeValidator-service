package com.rimiTest.CallCodeAndNumber.service;

import com.rimiTest.CallCodeAndNumber.exceptions.PhoneNumberServiceException;
import com.rimiTest.CallCodeAndNumber.persistence.DAO.CountryCodeAndCountryDAO;
import com.rimiTest.CallCodeAndNumber.persistence.model.request.PhoneNumberRequest;
import com.rimiTest.CallCodeAndNumber.persistence.model.response.PhoneNumberResponse;
import com.rimiTest.CallCodeAndNumber.service.impl.PhoneNumberServiceImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PhoneNumberServiceTest {
    private final CountryCodeAndCountryDAO countryCodeAndCountryDAO = mock(CountryCodeAndCountryDAO.class);
    private final PhoneNumberService phoneNumberService = new PhoneNumberServiceImpl(countryCodeAndCountryDAO);

    @Test
    public void getCountryByPhoneNumber_shouldThrowPhoneNumberServiceException_whenNumberContainsNonDigits() {
        String number = "12310sew";
        PhoneNumberRequest phoneNumberRequest = new PhoneNumberRequest();
        phoneNumberRequest.setCallCodeAndNumber(number);
        assertThrows(PhoneNumberServiceException.class, () -> {
            phoneNumberService.getCountryByPhoneNumber(phoneNumberRequest);
        });
    }

    @Test
    public void getCountryByPhoneNumber_shouldThrowPhoneNumberServiceException_whenNumberLengthLessThanEleven() {
        String number = "12310";
        PhoneNumberRequest phoneNumberRequest = new PhoneNumberRequest();
        phoneNumberRequest.setCallCodeAndNumber(number);
        assertThrows(PhoneNumberServiceException.class, () -> {
            phoneNumberService.getCountryByPhoneNumber(phoneNumberRequest);
        });
    }

    @Test
    public void getCountryByPhoneNumber_shouldReturnCountryName_whenCountryCodeWithLengthFiveMatches() {
        String number = "37231231234";
        PhoneNumberRequest phoneNumberRequest = new PhoneNumberRequest();
        phoneNumberRequest.setCallCodeAndNumber(number);
        when(countryCodeAndCountryDAO.getCountryByCountryCode("+37231")).thenReturn("Egypt");

        PhoneNumberResponse response = phoneNumberService.getCountryByPhoneNumber(phoneNumberRequest);
        assertEquals("Egypt", response.getCountry());

    }

    @Test
    public void getCountryByPhoneNumber_shouldReturnCountryName_whenCountryCodeWithLengthThreeMatches() {
        String number = "312331231234";
        PhoneNumberRequest phoneNumberRequest = new PhoneNumberRequest();
        phoneNumberRequest.setCallCodeAndNumber(number);
        when(countryCodeAndCountryDAO.getCountryByCountryCode("+31233")).thenReturn(null);
        when(countryCodeAndCountryDAO.getCountryByCountryCode("+3123")).thenReturn(null);
        when(countryCodeAndCountryDAO.getCountryByCountryCode("+312")).thenReturn("Estonia");

        PhoneNumberResponse response = phoneNumberService.getCountryByPhoneNumber(phoneNumberRequest);
        assertEquals("Estonia", response.getCountry());

    }

    @Test
    public void getCountryByPhoneNumber_shouldReturnCountryName_whenCountryCodeWithLengthOneMatches() {
        String number = "312331231234";
        PhoneNumberRequest phoneNumberRequest = new PhoneNumberRequest();
        phoneNumberRequest.setCallCodeAndNumber(number);
        when(countryCodeAndCountryDAO.getCountryByCountryCode("+3")).thenReturn("USA");
        PhoneNumberResponse response = phoneNumberService.getCountryByPhoneNumber(phoneNumberRequest);
        assertEquals("USA", response.getCountry());

    }

    @Test
    public void getCountryByPhoneNumber_shouldReturnNullValue_whenCountryCodeNotFound() {
        String number = "893-6671676767";
        PhoneNumberRequest phoneNumberRequest = new PhoneNumberRequest();
        phoneNumberRequest.setCallCodeAndNumber(number);
        when(countryCodeAndCountryDAO.getCountryByCountryCode("89")).thenReturn(null);
        PhoneNumberResponse response = phoneNumberService.getCountryByPhoneNumber(phoneNumberRequest);
        assertNull(response.getCountry());

    }
}
