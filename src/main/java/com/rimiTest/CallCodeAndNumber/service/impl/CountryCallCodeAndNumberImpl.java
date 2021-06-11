package com.rimiTest.CallCodeAndNumber.service.impl;


import com.rimiTest.CallCodeAndNumber.dto.CountryCallCodeAndNumberDto;
import com.rimiTest.CallCodeAndNumber.persistence.DAO.CountryCallCodeAndNumberDAO;
import com.rimiTest.CallCodeAndNumber.service.CountryCallCodeAndNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class CountryCallCodeAndNumberImpl implements CountryCallCodeAndNumberService {

     // Longest possible number: prefix + number + suffix: 5 + 15 + 11 = 31.
    private static final String DIGIT_PATTERN = "(\\d{11,31})";

    // From data source, no country prefix starts with 0.
    private static final String PREFIX_PATTERN = "^0+(?!$)";

    private static final String REPLACE_PATTERN = "[\\s+\\+\\-\\(\\)]";
    private static final String PLUS_SIGN = "+";

    private final CountryCallCodeAndNumberDAO countryCallCodeAndNumberDAO;

    @Autowired
    public CountryCallCodeAndNumberImpl(CountryCallCodeAndNumberDAO countryCallCodeAndNumberDAO) {
        this.countryCallCodeAndNumberDAO = countryCallCodeAndNumberDAO;
    }

     /**
     * Sanitizes input and Use regex to ensure input matches a particular digit pattern
     * Determines if a country call-code and phone number is valid
     *
     * @param  countryCallCodeAndNumber
     *        call-code and phone number string
     *
     * @return  True or False base on validity of country call-code and phone number
     */
    public boolean isValidCountryCodeAndNumber(String countryCallCodeAndNumber) {
        Pattern patternDigit = Pattern.compile(DIGIT_PATTERN);
        return patternDigit.matcher(matchedPrefixPattern(matchedNumberOfDigitPattern(countryCallCodeAndNumber))).matches();
    }

    /**
     * Constructs a proper country prefix- call code and phone number base on user input.
     * Main app logic here. Loop through hashmap country call-prefixes(key), while looking for country(value).
     * According to https://en.wikipedia.org/wiki/List_of_international_call_prefixes,
     * longest possible prefix has length of 5, excluding the "+" sign. Hence hashKeyCount -> 6.
     *
     * @param  countryCallCodeAndNumberDto
     *         DTO carrying user input (call-code and phone number) to the service
     *
     * @return  The retrieved country from the hashTable or null if no country found
     */
    @Override
    public CountryCallCodeAndNumberDto getCountryByCountryCallCodeAndNumber(CountryCallCodeAndNumberDto countryCallCodeAndNumberDto) {

        Optional<Map<String, String>> countryCallCodeAndNumber = Optional.ofNullable(countryCallCodeAndNumberDAO.getCallCodeAndNumberMap());
        String derivedCallCodeAndNumber = PLUS_SIGN + matchedPrefixPattern(matchedNumberOfDigitPattern(countryCallCodeAndNumberDto.getCallCodeAndNumber()));

        countryCallCodeAndNumber.ifPresent(countryCodeAndNumber -> {
            String country = null;
            int hashKeyCount = 6;

            while (0 < hashKeyCount) {
                country = countryCodeAndNumber.get(derivedCallCodeAndNumber.substring(0, hashKeyCount));

                if (country != null) {
                    break;
                }

                hashKeyCount--;
            }

            countryCallCodeAndNumberDto.setCountry(country);
        });

        return countryCallCodeAndNumberDto;
    }

    private String matchedNumberOfDigitPattern(String str){
        return str.replaceAll(REPLACE_PATTERN, "");
    }

    private String matchedPrefixPattern(String str){
        return str.replaceAll(PREFIX_PATTERN, "");
    }

}
