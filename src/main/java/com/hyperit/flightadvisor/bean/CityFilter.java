package com.hyperit.flightadvisor.bean;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CityFilter {

    String name;
    Integer noOfComments;

}
