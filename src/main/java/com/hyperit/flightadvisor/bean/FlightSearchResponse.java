package com.hyperit.flightadvisor.bean;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class FlightSearchResponse {

    private BigDecimal totalAmount= BigDecimal.ZERO;
    private List<RouteResponse> routes = new ArrayList<>();

}
