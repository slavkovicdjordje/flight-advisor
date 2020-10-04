package com.hyperit.flightadvisor.service;

import com.hyperit.flightadvisor.entity.Route;

import java.util.List;

/**
 * Service for route related operations.
 */
public interface RouteService {

    /**
     * Get all routes.
     */
    List<Route> findAll();

    /**
     * Save given list of routes.
     *
     * @param routes List of routes.
     */
    void saveAll(List<Route> routes);

}
