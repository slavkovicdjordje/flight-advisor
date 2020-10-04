package com.hyperit.flightadvisor.service;

import com.hyperit.flightadvisor.entity.Airport;

import java.util.List;
import java.util.Set;

/**
 * Service for airport related operations.
 */
public interface AirportService {

    /**
     * Get all airports.
     */
    List<Airport> findAll();

    /**
     * Get IDs of all airports.
     */
    Set<Integer> getAllIds();

    /**
     * Save given list of airports.
     *
     * @param airports List of airports.
     */
    void saveAll(List<Airport> airports);

}
