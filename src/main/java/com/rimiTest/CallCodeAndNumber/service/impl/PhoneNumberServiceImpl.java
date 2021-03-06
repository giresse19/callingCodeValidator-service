package com.rimiTest.CallCodeAndNumber.service.impl;


import com.rimiTest.CallCodeAndNumber.exceptions.PhoneNumberServiceException;
import com.rimiTest.CallCodeAndNumber.persistence.DAO.CountryCodeAndCountryDAO;
import com.rimiTest.CallCodeAndNumber.persistence.model.ErrorMessages;
import com.rimiTest.CallCodeAndNumber.persistence.model.request.PhoneNumberRequest;
import com.rimiTest.CallCodeAndNumber.persistence.model.response.PhoneNumberResponse;
import com.rimiTest.CallCodeAndNumber.service.PhoneNumberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@RequiredArgsConstructor
@Service
public class PhoneNumberServiceImpl implements PhoneNumberService {

    private static final String PHONE_PATTERN = "(\\d{11,31})";
    private static final String LEADING_ZEROS_PATTERN = "^0+(?!$)";
    private static final String SPECIAL_CHARS_PATTERN = "[\\s+\\+\\-\\(\\)]";
    private static final String PLUS_SIGN = "+";

    private final CountryCodeAndCountryDAO countryCodeAndCountryDAO;

    /**
     * According to https://en.wikipedia.org/wiki/List_of_international_call_prefixes,
     * longest possible country code length is 5.
     *
     * @param phoneNumberRequest user input (country-code and phone number).
     *
     * @return PhoneNumberResponse Returns all possible country(ies)
     * which has the call-code(key) on the hashTable or null if no country found.
     * e.g a number such as: +1242671676767, could still be either +1 340(the bahamas) or +1(US and Canada), base on
     * call codes check.
     */
    @Override
    public PhoneNumberResponse getCountryByPhoneNumber(PhoneNumberRequest phoneNumberRequest) {
        StringBuilder allPossibleCountries = new StringBuilder();

        String processedNumber = processRawNumber(phoneNumberRequest.getCallCodeAndNumber());
        validateNumber(processedNumber);

        String country = null;
        int countryCodeLength = 5;

        while (countryCodeLength > 0) {
            String countryCode = processedNumber.substring(0, countryCodeLength);
            country = countryCodeAndCountryDAO.getCountryByCountryCode(PLUS_SIGN + countryCode);

            if (country != null) {
                if(allPossibleCountries.length() > 0 ) allPossibleCountries.append(", ");
                allPossibleCountries.append(country);
            }

            countryCodeLength--;
        }

        return new PhoneNumberResponse(allPossibleCountries.toString());
    }

    private String processRawNumber(String rawNumber) {
        return rawNumber.replaceAll(SPECIAL_CHARS_PATTERN, "").replaceAll(LEADING_ZEROS_PATTERN, "");
    }

    private void validateNumber(String processedNumber) {
        Pattern patternDigit = Pattern.compile(PHONE_PATTERN);
        if (!patternDigit.matcher(processedNumber).matches()) {
            throw new PhoneNumberServiceException(ErrorMessages.INVALID_INPUT.getErrorMessage());
        }
    }
}
