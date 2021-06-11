package com.rimiTest.CallCodeAndNumber.configuration;

import com.rimiTest.CallCodeAndNumber.persistence.DAO.CountryCallCodeAndNumberDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 *Listens to @ContextRefreshedEvent from spring boot and fires up @initFetchedAPIData method, to fetch APi Data
 *
 */

@Component
public class ApplicationStartup implements ApplicationListener<ContextRefreshedEvent> {
    private final CountryCallCodeAndNumberDAO countryCallCodeAndNumberDAO;

    @Autowired
    public ApplicationStartup(CountryCallCodeAndNumberDAO countryCallCodeAndNumberDAO) {
        this.countryCallCodeAndNumberDAO = countryCallCodeAndNumberDAO;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        countryCallCodeAndNumberDAO.initFetchedAPIData();
    }
}
