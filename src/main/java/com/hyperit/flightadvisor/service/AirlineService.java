package com.hyperit.flightadvisor.service;

import com.hyperit.flightadvisor.entity.Airline;

import java.util.Set;

/**
 * Service for airline related operations.
 */
public interface AirlineService {

    /**
     * Save given list of airlines.
     *
     * @param airlines List of airlines.
     */
    void saveAll(Set<Airline> airlines);

}
