package com.hanghae5.hanghae5.config.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ErrorResponse {
    private final LocalDateTime timestamp = LocalDateTime.now();
    private int status;
    private List<String> message;
    private String error;

    public ErrorResponse(RuntimeException runtimeException){
        this.status = HttpStatus.BAD_REQUEST.value();
        this.error = runtimeException.getClass().toString();

        this.message = new ArrayList<>();
        this.message.add(runtimeException.getMessage());
    }

}
