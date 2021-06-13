package com.rimiTest.CallCodeAndNumber.persistence.DAO;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.rimiTest.CallCodeAndNumber.exceptions.FetchAndParseAPIDataException;
import com.rimiTest.CallCodeAndNumber.persistence.model.ErrorMessages;
import com.rimiTest.CallCodeAndNumber.utils.AppConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Repository;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Repository
@RequiredArgsConstructor
@Slf4j
public class CountryCodeAndCountryDAO {
    private final RestTemplateBuilder restTemplateBuilder;
    private final ObjectMapper objectMapper;

    private static final String DATA_RESOURCE_URL = AppConstants.DATA_RESOURCE_URL + "?query=SELECT%20%3Fcc%20%3FitemLabel%0AWHERE%20%7B%0A%20%20%3Fitem%20wdt%3AP474%20%3Fcc%20.%0A%20%20%3Fitem%20wdt%3AP297%20%20%3Falpha2%20.%0A%20%20SERVICE%20wikibase%3Alabel%20%7B%20bd%3AserviceParam%20wikibase%3Alanguage%20%22en%2Cen%22%20%20%7D%20%20%20%20%0A%7D%0Aorder%20by%20%3Fcc%20&format=json";
    private static final String VALUE = "value";
    private static final Map<String, String> countryCallCodeAndNumberMap = new HashMap<>();

    public String getCountryByCountryCode(String countryCode) {
        return countryCallCodeAndNumberMap.get(countryCode);
    }

    /**
     * Fetches and parse a list of country codes and countries.
     * Puts them into a hastTable(Hashmap) for easy lookup in the future.
     * Method is called everytime app re-starts.
     *
     * @throws FetchAndParseAPIDataException This prevents micro-service from start-up hence exposing the failures for easier debugging and bringing certainty,
     *                                       since without this data micro-service is pretty much useless.
     */
    public void fetchAndParseAPIData() {
        try {
            String json = restTemplateBuilder.build()
                    .getForObject(new URL(DATA_RESOURCE_URL).toURI(), String.class);
            JsonNode jsonNode = objectMapper.readTree(json);
            JsonNode resultsNode = jsonNode.get("results");
            ArrayNode bindings = (ArrayNode) resultsNode.get("bindings");
            log.debug("Fetched API data");

            bindings.forEach(r -> {
                String key = r.get("cc").get(VALUE).asText();
                String val = r.get("itemLabel").get(VALUE).asText();
                countryCallCodeAndNumberMap.put(key,
                        !countryCallCodeAndNumberMap.containsKey(key) ?
                                val : countryCallCodeAndNumberMap.get(key).concat(", ".concat(val)));
            });
        } catch (Exception e) {
            throw new FetchAndParseAPIDataException(ErrorMessages.API_DATA_NOT_FETCHED.getErrorMessage());
        }
    }
}



