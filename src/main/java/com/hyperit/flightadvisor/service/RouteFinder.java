package com.hyperit.flightadvisor.service;

import com.hyperit.flightadvisor.bean.FlightSearchResponse;
import com.hyperit.flightadvisor.entity.Airport;

import java.util.List;

/**
 * Service for route searching operations.
 */
public interface RouteFinder {
    /**
     * Find cheapest route in given list of routes.
     *
     * @param airportsFrom List of source airports.
     * @param airportsTo List of destination airports.
     */
    FlightSearchResponse find(List<Airport> airportsFrom, List<Airport> airportsTo);
}
