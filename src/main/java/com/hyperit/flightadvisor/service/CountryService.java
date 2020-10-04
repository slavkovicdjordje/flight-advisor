package com.hyperit.flightadvisor.service;

import com.hyperit.flightadvisor.entity.Country;

import java.util.List;

/**
 * Service for country related operations.
 */
public interface CountryService {

    /**
     * Find or create country.
     *
     * @param name Name of country.
     * @return country
     */
    Country findOrCreate(String name);

    /**
     * Get all countries.
     */
    List<Country> findAll();

}
