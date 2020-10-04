package com.hyperit.flightadvisor.bean;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class ExceptionResponse {

    private String message;
    private LocalDateTime date;

}
