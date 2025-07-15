package com.example.cargoservice.ExceptionHandlers;

import com.example.cargoservice.Extensions.Response;
import com.example.cargoservice.Extensions.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Response<Object>> handleIllegalArgumentException(
            IllegalArgumentException ex, WebRequest request) {

        logger.error("Illegal argument exception occurred: {}", ex.getMessage(), ex);

        Response<Object> response = Response.failure(
                "Geçersiz parametre: " + ex.getMessage(),
                StatusCode.BAD_REQUEST
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Response<Object>> handleRuntimeException(
            RuntimeException ex, WebRequest request) {

        logger.error("Runtime exception occurred: {}", ex.getMessage(), ex);

        Response<Object> response = Response.failure(
                "İşlem sırasında hata oluştu: " + ex.getMessage(),
                StatusCode.INTERNAL_SERVER_ERROR
        );

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response<Object>> handleGenericException(
            Exception ex, WebRequest request) {

        logger.error("Unexpected exception occurred: {}", ex.getMessage(), ex);

        Response<Object> response = Response.failure(
                "Beklenmeyen bir hata oluştu",
                StatusCode.INTERNAL_SERVER_ERROR
        );

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Response<Object>> handleNullPointerException(
            NullPointerException ex, WebRequest request) {

        logger.error("Null pointer exception occurred: {}", ex.getMessage(), ex);

        Response<Object> response = Response.failure(
                "Gerekli bilgiler eksik",
                StatusCode.BAD_REQUEST
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Redis bağlantı hatalarını yakalamak için
    @ExceptionHandler(org.springframework.dao.DataAccessException.class)
    public ResponseEntity<Response<Object>> handleDataAccessException(
            org.springframework.dao.DataAccessException ex, WebRequest request) {

        logger.error("Database access exception occurred: {}", ex.getMessage(), ex);

        Response<Object> response = Response.failure(
                "Veritabanı bağlantı hatası",
                StatusCode.INTERNAL_SERVER_ERROR
        );

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}