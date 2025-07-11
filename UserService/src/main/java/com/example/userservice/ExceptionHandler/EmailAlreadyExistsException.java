package com.example.userservice.ExceptionHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class EmailAlreadyExistsException extends RuntimeException {
    private static final Logger logger = LoggerFactory.getLogger(EmailAlreadyExistsException.class);
    public EmailAlreadyExistsException(Object dto) {

        super("This email already exists "+dto.toString());
        logger.error("Email already exists exception {}", dto);
    }
}
