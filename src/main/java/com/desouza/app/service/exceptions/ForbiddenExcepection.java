package com.desouza.app.service.exceptions;

public class ForbiddenExcepection extends RuntimeException {

    public ForbiddenExcepection(String msg) {
        super(msg);
    }
}
