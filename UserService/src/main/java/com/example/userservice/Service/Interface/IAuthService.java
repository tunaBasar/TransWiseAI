package com.example.userservice.Service.Interface;

import com.example.userservice.Dtos.AuthResponse;
import com.example.userservice.Dtos.LoginRequest;
import com.example.userservice.Dtos.RegisterRequestDto;
import com.example.userservice.Extensions.Response;

public interface IAuthService {

    Response<AuthResponse> register(RegisterRequestDto registerRequestDto);
    Response<AuthResponse> login(LoginRequest loginRequest);
}
