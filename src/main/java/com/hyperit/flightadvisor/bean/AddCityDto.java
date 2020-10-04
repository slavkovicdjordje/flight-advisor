package com.hyperit.flightadvisor.bean;

import lombok.Data;

@Data
public class AddCityDto {

    private Integer id;
    private String name;
    private String countryName;
    private String description;

}
