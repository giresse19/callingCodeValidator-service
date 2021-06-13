package com.rimiTest.CallCodeAndNumber.configuration;

import com.rimiTest.CallCodeAndNumber.persistence.DAO.CountryCodeAndCountryDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * Calls @fetchAndParseAPIData everytime app re-starts.
 */

@RequiredArgsConstructor
@Component
public class ApplicationStartup implements ApplicationListener<ContextRefreshedEvent> {
    private final CountryCodeAndCountryDAO countryCodeAndCountryDAO;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        countryCodeAndCountryDAO.fetchAndParseAPIData();
    }
}
