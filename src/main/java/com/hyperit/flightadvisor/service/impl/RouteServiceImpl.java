package com.hyperit.flightadvisor.service.impl;

import com.hyperit.flightadvisor.repository.RouteRepository;
import com.hyperit.flightadvisor.entity.Route;
import com.hyperit.flightadvisor.service.RouteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class RouteServiceImpl implements RouteService {

    private final RouteRepository routeRepository;

    @Override
    public List<Route> findAll() {
        log.debug("Getting all routes.");

        return routeRepository.findAll();
    }

    @Override
    public void saveAll(List<Route> routes) {
        log.debug("Saving list of given routes.");

        routeRepository.saveAll(routes);
    }
}
