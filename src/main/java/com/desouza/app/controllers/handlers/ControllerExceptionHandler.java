package com.desouza.app.controllers.handlers;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.desouza.app.dto.error.CustomError;
import com.desouza.app.service.exceptions.ForbiddenExcepection;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ForbiddenExcepection.class)
    public ResponseEntity<CustomError> forbidden(ForbiddenExcepection e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.FORBIDDEN;
        CustomError err = new CustomError(e.getMessage(), status.value(), request.getRequestURI(), Instant.now());
        return ResponseEntity.status(status).body(err);
    }

}
