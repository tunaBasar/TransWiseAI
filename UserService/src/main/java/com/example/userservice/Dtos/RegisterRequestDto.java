package com.example.userservice.Dtos;

import com.example.userservice.Model.Role;
import lombok.Data;

@Data
public class RegisterRequestDto {
    private String name;
    private String Surname;
    private String email;
    private String SerialNumber;
    private String password;
    private String phone;
    private Role role;
}
