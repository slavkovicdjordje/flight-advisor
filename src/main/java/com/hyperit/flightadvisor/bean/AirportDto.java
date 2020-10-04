package com.hyperit.flightadvisor.bean;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AirportDto {

    @CsvBindByPosition(position = 0)
    private Integer id;
    @CsvBindByPosition(position = 1)
    private String name;
    @CsvBindByPosition(position = 2)
    private String city;
    @CsvBindByPosition(position = 3)
    private String country;
    @CsvBindByPosition(position = 4)
    private String IATA;
    @CsvBindByPosition(position = 5)
    private String ICAO;
    @CsvBindByPosition(position = 6)
    private BigDecimal latitude;
    @CsvBindByPosition(position = 7)
    private BigDecimal longitude;
    @CsvBindByPosition(position = 8)
    private Integer altitude;
    @CsvBindByPosition(position = 9)
    private String timezone;
    @CsvBindByPosition(position = 10)
    private String DST;
    @CsvBindByPosition(position = 11)
    private String olsonTimeZone;
    @CsvBindByPosition(position = 12)
    private String type;
    @CsvBindByPosition(position = 13)
    private String source;

}
