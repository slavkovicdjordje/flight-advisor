package com.hyperit.flightadvisor.bean;

import lombok.Data;

import java.util.Date;

@Data
public class CommentDto {

    private Integer id;
    private String cityId;
    private String comment;
    private Date dateAdded;
    private Date dateModified;
}
