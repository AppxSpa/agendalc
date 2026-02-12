package com.agendalc.agendalc.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class OperacionNoPermitidaException extends RuntimeException {

    public OperacionNoPermitidaException(String message) {
        super(message);
    }

    public OperacionNoPermitidaException(String message, Throwable cause) {
        super(message, cause);
    }
}
