package com.hyperit.flightadvisor.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Accessors(chain = true)
@Table(name = "airlines")
public class Airline {

    @Id
    private Integer id;
    private String name;

}
