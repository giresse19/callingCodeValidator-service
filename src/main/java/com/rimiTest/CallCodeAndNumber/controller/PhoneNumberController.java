package com.rimiTest.CallCodeAndNumber.controller;


import com.rimiTest.CallCodeAndNumber.persistence.model.request.PhoneNumberRequest;
import com.rimiTest.CallCodeAndNumber.persistence.model.response.PhoneNumberResponse;
import com.rimiTest.CallCodeAndNumber.service.PhoneNumberService;
import com.rimiTest.CallCodeAndNumber.utils.UrlMappings;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(UrlMappings.SEARCH_PHONE_NUM)
@RequiredArgsConstructor
public class PhoneNumberController {

    private final PhoneNumberService phoneNumberService;

    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<PhoneNumberResponse> getCountryByCallCodeAndNumber(@Valid @RequestBody PhoneNumberRequest phoneNumberRequest) {
        PhoneNumberResponse searchedResponse = phoneNumberService.getCountryByPhoneNumber(phoneNumberRequest);
        if (searchedResponse.getCountry() == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return ResponseEntity.ok(searchedResponse);
    }

}
