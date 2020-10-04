package com.hyperit.flightadvisor.service;

import com.hyperit.flightadvisor.bean.CitySearchResponse;
import com.hyperit.flightadvisor.entity.City;

import java.util.List;

/**
 * Service for city related operations.
 */
public interface CityService {

    /**
     * Save new city.
     *
     * @param city {@link City}
     * @return saved city
     */
    City addCity(City city);

    /**
     * Find cities.
     *
     * @param cityName     Name of city to search for.
     * @param noOfComments Limit for number of comments to be returned.
     * @return List of found cities.
     */
    List<CitySearchResponse> findCities(String cityName, Integer noOfComments);
}
