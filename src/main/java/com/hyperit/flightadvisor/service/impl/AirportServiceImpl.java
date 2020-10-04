package com.hyperit.flightadvisor.service.impl;

import com.hyperit.flightadvisor.repository.AirportRepository;
import com.hyperit.flightadvisor.entity.Airport;
import com.hyperit.flightadvisor.service.AirportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Log4j2
@Service
@RequiredArgsConstructor
public class AirportServiceImpl implements AirportService {

    private final AirportRepository airportRepository;

    @Override
    public List<Airport> findAll() {
        log.debug("Fetching list of all airports.");

        return airportRepository.findAll();
    }

    @Override
    public Set<Integer> getAllIds() {
        log.debug("Fetching list of IDs of all airports.");

        return airportRepository.getAllIds();
    }

    @Override
    public void saveAll(List<Airport> airports) {
        log.debug("Saving list of airports.");

        airportRepository.saveAll(airports);
    }

}
