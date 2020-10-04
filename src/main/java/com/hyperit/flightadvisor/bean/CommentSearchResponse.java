package com.hyperit.flightadvisor.bean;

import lombok.Data;

import java.util.Date;

@Data
public class CommentSearchResponse {

    private Integer id;
    private String text;
    private Date dateAdded;
}
