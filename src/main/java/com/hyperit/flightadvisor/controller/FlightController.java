package com.hyperit.flightadvisor.controller;

import com.hyperit.flightadvisor.bean.FlightSearchResponse;
import com.hyperit.flightadvisor.exception.BadRequestException;
import com.hyperit.flightadvisor.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("flights")
//@PreAuthorize("hasRole('ROLE_USER')")
public class FlightController {

    private final FlightService flightService;

    @GetMapping
    public FlightSearchResponse getFlight(@RequestParam(value = "fromCity") Integer fromCity,
                                          @RequestParam(value = "toCity") Integer toCity) {

        if (fromCity.equals(toCity)) {
            throw new BadRequestException("Start city can't be the same as destination city.");
        }
        return flightService.findFlight(fromCity, toCity);
    }

}
