package com.hanghae5.hanghae5.config.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {
    private final LocalDateTime timestamp = LocalDateTime.now();
    private int status;
    private List<String> message;
    private String error;

    public ErrorResponse(RuntimeException runtimeException){
        this.status = HttpStatus.BAD_REQUEST.value();
        this.error = HttpStatus.BAD_REQUEST.name();

        this.message = new ArrayList<>();
        this.message.add(runtimeException.getMessage());
    }

    public ErrorResponse(MethodArgumentNotValidException methodArgumentNotValidException){

        this.status = HttpStatus.BAD_REQUEST.value();
        this.error = HttpStatus.BAD_REQUEST.name();

        this.message = methodArgumentNotValidException.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getField() + " : " + x.getDefaultMessage())
                .collect(Collectors.toList());
    }
}
