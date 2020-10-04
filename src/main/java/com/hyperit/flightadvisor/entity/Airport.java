package com.hyperit.flightadvisor.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Accessors(chain = true)
@Table(name = "airports")
public class Airport {

    @Id
    private Integer id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;
    private String IATA;
    private String ICAO;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Integer altitude;
    private String timezone;
    private String DST;
    private String olson;
    private String type;
    private String source;

}
