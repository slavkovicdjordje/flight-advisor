package com.hyperit.flightadvisor.service.impl;

import com.hyperit.flightadvisor.repository.CountryRepository;
import com.hyperit.flightadvisor.entity.Country;
import com.hyperit.flightadvisor.service.CountryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;

    @Override
    public Country findOrCreate(String name) {
        log.debug("Finding country by name or save if not present.");

        Country country = countryRepository.findByName(name);
        if (country == null) {
            country = new Country();
            country.setName(name);
            country =  countryRepository.save(country);
        }

        return country;
    }

    @Override
    public List<Country> findAll() {
        log.debug("Fetching list of all countries.");

        return countryRepository.findAll();
    }

}
