package com.hyperit.flightadvisor.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Accessors(chain = true)
@Table(name = "routes")
public class Route {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne
    @JoinColumn(name = "airline_id")
    private Airline airline;
    @ManyToOne
    @JoinColumn(name = "source_airport_id")
    private Airport sourceAirport;
    @ManyToOne
    @JoinColumn(name = "destination_airport_id")
    private Airport destinationAirport;
    private char codeshare;
    private Integer stops;
    private String equipment;
    private BigDecimal price;

}
