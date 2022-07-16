package com.hanghae5.hanghae5.config.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RuntimeExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity validationErrorException(final RuntimeException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(e));
    }
}
