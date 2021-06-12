package com.rimiTest.CallCodeAndNumber.service;

import com.rimiTest.CallCodeAndNumber.exceptions.CountryCallCodeAndNumberServiceException;
import com.rimiTest.CallCodeAndNumber.persistence.DAO.CountryCallCodeAndNumberDAO;
import com.rimiTest.CallCodeAndNumber.persistence.model.request.CountryCallCodeAndNumberRequest;
import com.rimiTest.CallCodeAndNumber.persistence.model.response.CountryCallCodeAndNumberResponse;
import com.rimiTest.CallCodeAndNumber.service.impl.CountryCallCodeAndNumberServiceImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CountryCallCodeAndNumberServiceTest {
    private final CountryCallCodeAndNumberDAO countryCallCodeAndNumberDAO = mock(CountryCallCodeAndNumberDAO.class);
    private final CountryCallCodeAndNumberService countryCallCodeAndNumberService = new CountryCallCodeAndNumberServiceImpl(countryCallCodeAndNumberDAO);

    @Test
    public void getCountryByPhoneNumber_shouldThrowCountryCallCodeAndNumberServiceException_whenNumberContainsNonDigits() {
        String number = "12310sew";
        CountryCallCodeAndNumberRequest countryCallCodeAndNumberRequest = new CountryCallCodeAndNumberRequest();
        countryCallCodeAndNumberRequest.setCallCodeAndNumber(number);
        assertThrows(CountryCallCodeAndNumberServiceException.class, () -> {
            countryCallCodeAndNumberService.getCountryByPhoneNumber(countryCallCodeAndNumberRequest);
        });
    }

    @Test
    public void getCountryByPhoneNumber_shouldThrowCountryCallCodeAndNumberServiceException_whenNumberLengthLessThanEleven() {
        String number = "12310";
        CountryCallCodeAndNumberRequest countryCallCodeAndNumberRequest = new CountryCallCodeAndNumberRequest();
        countryCallCodeAndNumberRequest.setCallCodeAndNumber(number);
        assertThrows(CountryCallCodeAndNumberServiceException.class, () -> {
            countryCallCodeAndNumberService.getCountryByPhoneNumber(countryCallCodeAndNumberRequest);
        });
    }

    // todo test more cases
    @Test
    public void getCountryByPhoneNumber_shouldReturnCountryName_whenCountryCodeWithLengthFiveMatches() {
        String number = "37231231234";
        CountryCallCodeAndNumberRequest countryCallCodeAndNumberRequest = new CountryCallCodeAndNumberRequest();
        countryCallCodeAndNumberRequest.setCallCodeAndNumber(number);
        when(countryCallCodeAndNumberDAO.getCountryByCountryCode("+37231")).thenReturn("Egypt");

        CountryCallCodeAndNumberResponse response = countryCallCodeAndNumberService.getCountryByPhoneNumber(countryCallCodeAndNumberRequest);
        assertEquals("Egypt", response.getCountry());

    }

    @Test
    public void getCountryByPhoneNumber_shouldReturnCountryName_whenCountryCodeWithLengthThreeMatches() {
        String number = "312331231234";
        CountryCallCodeAndNumberRequest countryCallCodeAndNumberRequest = new CountryCallCodeAndNumberRequest();
        countryCallCodeAndNumberRequest.setCallCodeAndNumber(number);
        when(countryCallCodeAndNumberDAO.getCountryByCountryCode("+31233")).thenReturn(null);
        when(countryCallCodeAndNumberDAO.getCountryByCountryCode("+3123")).thenReturn(null);
        when(countryCallCodeAndNumberDAO.getCountryByCountryCode("+312")).thenReturn("Estonia");

        CountryCallCodeAndNumberResponse response = countryCallCodeAndNumberService.getCountryByPhoneNumber(countryCallCodeAndNumberRequest);
        assertEquals("Estonia", response.getCountry());

    }

    @Test
    public void getCountryByPhoneNumber_shouldReturnCountryName_whenCountryCodeWithLengthOneMatches() {
        String number = "312331231234";
        CountryCallCodeAndNumberRequest countryCallCodeAndNumberRequest = new CountryCallCodeAndNumberRequest();
        countryCallCodeAndNumberRequest.setCallCodeAndNumber(number);
        when(countryCallCodeAndNumberDAO.getCountryByCountryCode("+3")).thenReturn("USA");
        CountryCallCodeAndNumberResponse response = countryCallCodeAndNumberService.getCountryByPhoneNumber(countryCallCodeAndNumberRequest);
        assertEquals("USA", response.getCountry());

    }
}
