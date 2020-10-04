package com.hyperit.flightadvisor.exception;

public class UploadException extends RuntimeException {

    public UploadException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
