package com.rimiTest.CallCodeAndNumber.service.impl;


import com.rimiTest.CallCodeAndNumber.exceptions.CountryCallCodeAndNumberServiceException;
import com.rimiTest.CallCodeAndNumber.persistence.DAO.CountryCallCodeAndNumberDAO;
import com.rimiTest.CallCodeAndNumber.persistence.model.ErrorMessages;
import com.rimiTest.CallCodeAndNumber.persistence.model.request.CountryCallCodeAndNumberRequest;
import com.rimiTest.CallCodeAndNumber.persistence.model.response.CountryCallCodeAndNumberResponse;
import com.rimiTest.CallCodeAndNumber.service.CountryCallCodeAndNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.regex.Pattern;

@Service
public class CountryCallCodeAndNumberServiceImpl implements CountryCallCodeAndNumberService {

    private static final String PHONE_PATTERN = "(\\d{11,31})";
    private static final String LEADING_ZEROS_PATTERN = "^0+(?!$)";
    private static final String SPECIAL_CHARS_PATTERN = "[\\s+\\+\\-\\(\\)]";
    private static final String PLUS_SIGN = "+";

    private final CountryCallCodeAndNumberDAO countryCallCodeAndNumberDAO;

    @Autowired
    public CountryCallCodeAndNumberServiceImpl(CountryCallCodeAndNumberDAO countryCallCodeAndNumberDAO) {
        this.countryCallCodeAndNumberDAO = countryCallCodeAndNumberDAO;
    }

    /**
     * Constructs a proper country prefix- call code and phone number base on user input.
     * Main app logic here. Loop through hashmap country call-prefixes(key), while looking for country(value).
     * According to https://en.wikipedia.org/wiki/List_of_international_call_prefixes,
     * longest possible prefix has length is 5.
     *
     * @param countryCallCodeAndNumberRequest request carrying user input (call-code and phone number) to the service
     * @return The retrieved country from the hashTable or null if no country found
     */
    @Override
    public CountryCallCodeAndNumberResponse getCountryByPhoneNumber(CountryCallCodeAndNumberRequest countryCallCodeAndNumberRequest) {
        String processedNumber = processRawNumber(countryCallCodeAndNumberRequest.getCallCodeAndNumber());
        validateNumber(processedNumber);
        String country = null;
        int countryCodeLength = 5;

        while (countryCodeLength > 0) {
            String countryCode = processedNumber.substring(0, countryCodeLength);
            country = countryCallCodeAndNumberDAO.getCountryByCountryCode(PLUS_SIGN + countryCode);

            if (country != null) {
                break;
            }

            countryCodeLength--;
        }
        return   new CountryCallCodeAndNumberResponse(country);
    }

        private String processRawNumber(String rawNumber) {
        return rawNumber.replaceAll(SPECIAL_CHARS_PATTERN, "").replaceAll(LEADING_ZEROS_PATTERN, "");
    }

     private void validateNumber(String processedNumber) {

         Pattern patternDigit = Pattern.compile(PHONE_PATTERN);
         if (!patternDigit.matcher(processedNumber).matches()) {
             throw new CountryCallCodeAndNumberServiceException(ErrorMessages.INVALID_INPUT.getErrorMessage());
         }
     }
}
