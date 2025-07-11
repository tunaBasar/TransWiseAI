package com.example.userservice.Service.Interface;

import com.example.userservice.Dtos.UserResponseDto;
import com.example.userservice.Extensions.Response;

import java.util.List;

public interface IUserService {


    Response<List<UserResponseDto>> GetAllUsers();
    Response<UserResponseDto> GetUserBySerialNumber(String serialNumber);
}
