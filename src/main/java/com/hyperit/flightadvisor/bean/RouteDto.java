package com.hyperit.flightadvisor.bean;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RouteDto {

    @CsvBindByPosition(position = 0)
    private String airline;
    @CsvBindByPosition(position = 1)
    private String airlineId;
    @CsvBindByPosition(position = 2)
    private String sourceAirport;
    @CsvBindByPosition(position = 3)
    private String sourceAirportId;
    @CsvBindByPosition(position = 4)
    private String destinationAirport;
    @CsvBindByPosition(position = 5)
    private String destinationAirportId;
    @CsvBindByPosition(position = 6)
    private char codeshare;
    @CsvBindByPosition(position = 7)
    private Integer stops;
    @CsvBindByPosition(position = 8)
    private String equipment;
    @CsvBindByPosition(position = 9)
    private BigDecimal price;

}

