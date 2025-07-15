package com.example.userservice.Controller;

import com.example.userservice.Dtos.AuthResponse;
import com.example.userservice.Dtos.LoginRequest;
import com.example.userservice.Dtos.RegisterRequestDto;
import com.example.userservice.Extensions.Response;
import com.example.userservice.Service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<Response<AuthResponse>> register(@RequestBody RegisterRequestDto registerRequestDto) {
            Response<AuthResponse> authResponse = authService.register(registerRequestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(authResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<Response<AuthResponse>> login(@RequestBody @Valid LoginRequest loginRequest) {
            Response<AuthResponse> authResponse = authService.login(loginRequest);
            return ResponseEntity.status(HttpStatus.OK).body(authResponse);

    }

}
