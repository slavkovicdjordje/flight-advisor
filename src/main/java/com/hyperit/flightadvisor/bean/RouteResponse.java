package com.hyperit.flightadvisor.bean;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class RouteResponse {

    private String source;
    private String destination;
    private BigDecimal amount;

}
