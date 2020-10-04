package com.hyperit.flightadvisor.service.impl;

import com.hyperit.flightadvisor.bean.FlightSearchResponse;
import com.hyperit.flightadvisor.exception.BadRequestException;
import com.hyperit.flightadvisor.entity.Airport;
import com.hyperit.flightadvisor.service.AirportService;
import com.hyperit.flightadvisor.service.FlightService;
import com.hyperit.flightadvisor.service.RouteFinder;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.lang.String.format;

@Log4j2
@Service
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {

    private final RouteFinder routeFinder;
    private final AirportService airportService;

    @Override
    public FlightSearchResponse findFlight(Integer fromCity, Integer toCity) {
        log.debug(format("Searching for cheapest flight from city #%d to city #%d", fromCity, toCity));

        List<Airport> airportsFrom = getCityAirports(fromCity);
        List<Airport> airportsTo = getCityAirports(toCity);

        return routeFinder.find(airportsFrom, airportsTo);
    }

    /**
     * Get all airports for given cityId.
     */
    private List<Airport> getCityAirports(Integer cityId) {
        List<Airport> allAirports = airportService.findAll();

        List<Airport> result = new ArrayList<>();
        allAirports.forEach(airport -> {
            if (airport.getCity().getId().equals(cityId)) {
                result.add(airport);
            }
        });

        if (result.isEmpty()) {
            throw new BadRequestException("There is no city or airports for city with ID " + cityId);
        }

        return result;
    }

}
