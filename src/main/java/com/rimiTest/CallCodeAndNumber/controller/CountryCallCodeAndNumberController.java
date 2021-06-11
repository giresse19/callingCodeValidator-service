package com.rimiTest.CallCodeAndNumber.controller;


import com.rimiTest.CallCodeAndNumber.dto.CountryCallCodeAndNumberDto;
import com.rimiTest.CallCodeAndNumber.exceptions.CountryCallCodeAndNumberServiceException;
import com.rimiTest.CallCodeAndNumber.persistence.model.request.CountryCallCodeAndNumberRequest;
import com.rimiTest.CallCodeAndNumber.persistence.model.response.CountryCallCodeAndNumberResponse;
import com.rimiTest.CallCodeAndNumber.persistence.model.ErrorMessages;
import com.rimiTest.CallCodeAndNumber.service.CountryCallCodeAndNumberService;
import com.rimiTest.CallCodeAndNumber.utils.UrlMappings;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


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
     * @param  countryCallCodeAndNumberRequest
     * Takes the input from the user as Json Obj
     *
     * @return  Returns Json Obj of CallCodeAndNumberResponse to the user or null if no country found.
     */
    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CountryCallCodeAndNumberResponse> getCountryByCallCodeAndNumber(@Valid @RequestBody CountryCallCodeAndNumberRequest countryCallCodeAndNumberRequest) {
        CountryCallCodeAndNumberResponse responseResult = new CountryCallCodeAndNumberResponse();

        ModelMapper modelMapper = new ModelMapper();
        CountryCallCodeAndNumberDto countryCallCodeAndNumberDto = modelMapper.map(countryCallCodeAndNumberRequest, CountryCallCodeAndNumberDto.class);
        CountryCallCodeAndNumberDto SearchedResponse = countryCallCodeAndNumberService.getCountryByCountryCallCodeAndNumber(countryCallCodeAndNumberDto);

        if (!countryCallCodeAndNumberService.isValidCountryCodeAndNumber(countryCallCodeAndNumberDto.getCallCodeAndNumber())) {
            throw new CountryCallCodeAndNumberServiceException(ErrorMessages.INVALID_INPUT.getErrorMessage());
        }

        CountryCallCodeAndNumberResponse countryCallCodeAndNumberResponse = new CountryCallCodeAndNumberResponse();
        BeanUtils.copyProperties(SearchedResponse, countryCallCodeAndNumberResponse);

        if (countryCallCodeAndNumberResponse.getCountry() == null) {
            responseResult.setCountry(ErrorMessages.NO_COUNTRY_PREFIX_MATCHED.getErrorMessage());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(responseResult);
        }

        return  ResponseEntity.ok(countryCallCodeAndNumberResponse);
    }

}
