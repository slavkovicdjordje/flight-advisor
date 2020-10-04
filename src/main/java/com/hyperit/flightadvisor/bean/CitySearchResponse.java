package com.hyperit.flightadvisor.bean;

import com.hyperit.flightadvisor.entity.Comment;
import lombok.Data;

import java.util.List;

@Data
public class CitySearchResponse {

    private Integer id;
    private String name;
    private String countryName;
    private String description;
    private List<Comment> comments;

}
