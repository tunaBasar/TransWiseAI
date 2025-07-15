package com.example.userservice.ExceptionHandler;

import com.example.userservice.Extensions.Response;
import com.example.userservice.Extensions.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // Bu handler artık sadece method-level security exception'ları için
    // çünkü HTTP-level security exception'ları CustomAccessDeniedHandler ve
    // CustomAuthenticationEntryPoint tarafından handle edilecek
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Response<Object>> handleAccessDeniedException(AccessDeniedException e) {
        log.warn("Method-level access denied: {}", e.getMessage());

        Response<Object> response = Response.failure(
                "Bu işlem için yetkiniz bulunmuyor",
                StatusCode.UNAUTHORIZED.getCode()
        );

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Response<Object>> handleAuthenticationException(AuthenticationException e) {
        log.warn("Authentication failed: {}", e.getMessage());

        Response<Object> response = Response.failure(
                "Kimlik doğrulama başarısız",
                StatusCode.UNAUTHORIZED.getCode()
        );

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Response<Object>> handleBadCredentialsException(BadCredentialsException e) {
        log.warn("Bad credentials: {}", e.getMessage());

        Response<Object> response = Response.failure(
                "Kullanıcı adı veya şifre hatalı",
                StatusCode.UNAUTHORIZED.getCode()
        );

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<Response<Object>> handleEmailAlreadyExistsException(EmailAlreadyExistsException e) {
        log.warn("Email already exists: {}", e.getMessage());

        Response<Object> response = Response.failure(
                e.getMessage(),
                StatusCode.CONFLICT.getCode()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<Response<Object>> handleEmailNotFoundException(EmailNotFoundException e) {
        log.warn("Email not found: {}", e.getMessage());

        Response<Object> response = Response.failure(
                e.getMessage(),
                StatusCode.NOT_FOUND.getCode()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<Response<Object>> handleInvalidCredentialsException(InvalidCredentialsException e) {
        log.warn("Invalid credentials: {}", e.getMessage());

        Response<Object> response = Response.failure(
                e.getMessage(),
                StatusCode.UNAUTHORIZED.getCode()
        );

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(SerialNumberAlreadyExistException.class)
    public ResponseEntity<Response<Object>> handleSerialNumberAlreadyExistException(SerialNumberAlreadyExistException e) {
        log.warn("Serial number already exists: {}", e.getMessage());

        Response<Object> response = Response.failure(
                e.getMessage(),
                StatusCode.CONFLICT.getCode()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response<Object>> handleGeneralException(Exception e) {
        log.error("Unexpected error occurred: {}", e.getMessage(), e);

        Response<Object> response = Response.failure(
                "Beklenmeyen bir hata oluştu",
                StatusCode.INTERNAL_SERVER_ERROR.getCode()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}