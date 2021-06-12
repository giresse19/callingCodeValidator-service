package com.rimiTest.CallCodeAndNumber.controller;


import com.rimiTest.CallCodeAndNumber.persistence.model.request.CountryCallCodeAndNumberRequest;
import com.rimiTest.CallCodeAndNumber.persistence.model.response.CountryCallCodeAndNumberResponse;
import com.rimiTest.CallCodeAndNumber.service.CountryCallCodeAndNumberService;
import com.rimiTest.CallCodeAndNumber.utils.UrlMappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(UrlMappings.SEARCH_PHONE_NUM)
public class CountryCallCodeAndNumberController {

    private final CountryCallCodeAndNumberService countryCallCodeAndNumberService;


    @Autowired
    public CountryCallCodeAndNumberController(CountryCallCodeAndNumberService countryCallCodeAndNumberService) {
        this.countryCallCodeAndNumberService = countryCallCodeAndNumberService;
    }

    /**
     * Handles users request and response. consumes and produces json.
     *
     * @param countryCallCodeAndNumberRequest Takes the input from the user as Json Obj
     * @return Returns Json Obj of CallCodeAndNumberResponse to the user or null if no country found.
     */

    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<CountryCallCodeAndNumberResponse> getCountryByCallCodeAndNumber(@Valid @RequestBody CountryCallCodeAndNumberRequest countryCallCodeAndNumberRequest) {

               CountryCallCodeAndNumberResponse searchedResponse = countryCallCodeAndNumberService
                .getCountryByPhoneNumber(countryCallCodeAndNumberRequest);

        if (searchedResponse.getCountry() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.ok(searchedResponse);
    }

}
