package com.hyperit.flightadvisor.service;

import com.hyperit.flightadvisor.bean.FlightSearchResponse;

/**
 * Service for flight related operations.
 */
public interface FlightService {

    /**
     * Find cheapest flight between two cities.
     *
     * @param fromCity Id of starting city.
     * @param toCity   Id of destination city.
     * @return FlightSearchResponse
     */
    FlightSearchResponse findFlight(Integer fromCity, Integer toCity);

}
