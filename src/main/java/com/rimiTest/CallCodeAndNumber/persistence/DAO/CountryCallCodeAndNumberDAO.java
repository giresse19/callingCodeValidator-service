package com.rimiTest.CallCodeAndNumber.persistence.DAO;

import com.rimiTest.CallCodeAndNumber.exceptions.CountryCallCodeAndNumberServiceException;
import com.rimiTest.CallCodeAndNumber.persistence.model.ErrorMessages;
import com.rimiTest.CallCodeAndNumber.utils.AppConstants;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Repository;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
@Slf4j
public class CountryCallCodeAndNumberDAO {
    private final RestTemplateBuilder restTemplateBuilder;

    private static final String DATA_RESOURCE_URL = AppConstants.DATA_RESOURCE_URL + "?query=SELECT%20%3Fcc%20%3FitemLabel%0AWHERE%20%7B%0A%20%20%3Fitem%20wdt%3AP474%20%3Fcc%20.%0A%20%20%3Fitem%20wdt%3AP297%20%20%3Falpha2%20.%0A%20%20SERVICE%20wikibase%3Alabel%20%7B%20bd%3AserviceParam%20wikibase%3Alanguage%20%22en%2Cen%22%20%20%7D%20%20%20%20%0A%7D%0Aorder%20by%20%3Fcc%20&format=json";
    private static final String VALUE = "value";
    private Map<String, String> countryCallCodeAndNumberMap;

    public CountryCallCodeAndNumberDAO(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplateBuilder = restTemplateBuilder;
    }

    /**
     * Returns an optional hashmap containing country call-codes prefix as key and  countries as values.
     *
     * @return An optional hashmap containing call-codes and countries, retrieved from data source
     * @throws CountryCallCodeAndNumberServiceException If null is returned
     */
    public Map<String, String> getCallCodeAndNumberMap() {
        return Optional.ofNullable(countryCallCodeAndNumberMap).orElseGet(() -> {
            log.error("######### Fetching API data Failed #########");
            throw new CountryCallCodeAndNumberServiceException(ErrorMessages.API_DATA_NOT_FETCHED.getErrorMessage());
        });

    }

    /**
     * Fetches a list of country prefix call-codes and countries, and populate a JSONObject.
     * Puts them into a hastTable(Hashmap) for easy lookup in the future.
     * Method is called everytime app re-starts.
     */
    public void initFetchedAPIData() {
        log.info("######### Fetching API data #########");
        long start = System.currentTimeMillis();

        try {
            JSONObject fetchedCallCodeAndCountryResponse = new JSONObject(restTemplateBuilder.build().getForObject(new URL(DATA_RESOURCE_URL).toURI(), String.class));

            log.info("######### Got API data , took: " + (System.currentTimeMillis() - start) + " ms ###########");

            fetchedCallCodeAndCountryResponse = fetchedCallCodeAndCountryResponse.getJSONObject("results");
            JSONArray fetchedCallingCodeAndCountryResponseArr = fetchedCallCodeAndCountryResponse.getJSONArray("bindings");

            countryCallCodeAndNumberMap = new HashMap<>();

            fetchedCallingCodeAndCountryResponseArr.forEach(el -> {
                JSONObject row = (JSONObject) el;
                String key = (String) row.getJSONObject("cc").get(VALUE);
                String val = (String) row.getJSONObject("itemLabel").get(VALUE);
                countryCallCodeAndNumberMap.put(key,
                        !countryCallCodeAndNumberMap.containsKey(key) ? val : countryCallCodeAndNumberMap.get(key).concat(", ".concat(val)));
            });
        } catch (Exception e) {
            log.error("Error while fetching CallCodeAndCountryResponse", e);
        }
    }
}



