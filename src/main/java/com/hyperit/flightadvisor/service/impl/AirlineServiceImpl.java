package com.hyperit.flightadvisor.service.impl;

import com.hyperit.flightadvisor.repository.AirlineRepository;
import com.hyperit.flightadvisor.entity.Airline;
import com.hyperit.flightadvisor.service.AirlineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Set;

@Log4j2
@Service
@RequiredArgsConstructor
public class AirlineServiceImpl implements AirlineService {

    private final AirlineRepository airlineRepository;

    @Override
    public void saveAll(Set<Airline> airlines) {
        log.debug("Saving list of given airlines.");

        airlineRepository.saveAll(airlines);
    }
}
