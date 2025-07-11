package com.example.userservice.ExceptionHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InvalidCredentialsException extends RuntimeException {

    private static final Logger logger = LoggerFactory.getLogger(InvalidCredentialsException.class);
    public InvalidCredentialsException(String message) {
        super(message);
        logger.error(message);
    }
}
