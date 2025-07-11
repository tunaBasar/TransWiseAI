package com.example.userservice.Dtos;

import com.example.userservice.Model.Role;
import lombok.Data;

@Data
public class UserResponseDto {
    private String id;
    private String Email;
    private String SerialNumber;
    private Role role;
    private String name;
    private String surname;
    private String phone;
}
