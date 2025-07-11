package com.example.userservice.ExceptionHandler;

import com.example.userservice.Extensions.Response;
import com.example.userservice.Extensions.StatusCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;




@RestControllerAdvice()
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<Response<Object>> handleInvalidCredentials(InvalidCredentialsException ex) {
        return ResponseEntity
                .status(StatusCode.UNAUTHORIZED.getCode())
                .body(Response.failure(ex.getMessage(), HttpStatus.UNAUTHORIZED.value()));
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<Response<Object>> handleEmailAlreadyExists(EmailAlreadyExistsException ex) {
        return ResponseEntity
                .status(StatusCode.CONFLICT.getCode())
                .body(Response.failure(ex.getMessage(), HttpStatus.CONFLICT.value()));
    }

    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<Response<Object>> handleEmailNotFound(EmailNotFoundException ex) {
        return ResponseEntity
                .status(StatusCode.NOT_FOUND.getCode())
                .body(Response.failure(ex.getMessage(), HttpStatus.NOT_FOUND.value()));
    }

    @ExceptionHandler(SerialNumberAlreadyExistException.class)
    public ResponseEntity<Response<Object>> handleSerialNumberAlreadyExist(SerialNumberAlreadyExistException ex)
    {
        return ResponseEntity
                .status(StatusCode.CONFLICT.getCode())
                .body(Response.failure(ex.getMessage(),HttpStatus.CONFLICT.value()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response<Object>> handleValidationErrors(MethodArgumentNotValidException ex) {
        String msg = ex.getBindingResult().getFieldError().getDefaultMessage();
        return ResponseEntity
                .status(StatusCode.BAD_REQUEST.getCode())
                .body(Response.failure(msg, HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response<Object>> handleGeneral(Exception ex) {
        ex.printStackTrace();
        return ResponseEntity
                .status(StatusCode.INTERNAL_SERVER_ERROR.getCode())
                .body(Response.failure("Bilinmeyen bir hata oluştu", 500));
    }
}
