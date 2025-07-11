package com.example.userservice.ExceptionHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SerialNumberAlreadyExistException extends RuntimeException{
    private static final Logger logger = LoggerFactory.getLogger(SerialNumberAlreadyExistException.class);
    public  SerialNumberAlreadyExistException(Object dto)
    {
        super("Serial number already exists"+dto.toString());
        logger.error("SerialNumber already exists exception{}", dto);
    }
}
