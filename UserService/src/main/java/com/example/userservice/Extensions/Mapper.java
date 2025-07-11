package com.example.userservice.Extensions;

import com.example.userservice.Dtos.RegisterRequestDto;
import com.example.userservice.Dtos.UserResponseDto;
import com.example.userservice.Model.UserModel;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    public UserModel toEntity(RegisterRequestDto registerRequestDto) {
        UserModel user = new UserModel() ;

        user.setName(registerRequestDto.getName());
        user.setSurname(registerRequestDto.getSurname());
        user.setEmail(registerRequestDto.getEmail());
        user.setPassword(registerRequestDto.getPassword());
        user.setSerialNumber(registerRequestDto.getSerialNumber());
        user.setPhone(registerRequestDto.getPhone());
        user.setRole(registerRequestDto.getRole());

        return user;
    }

    public UserResponseDto toResponseDto(UserModel userModel) {
        UserResponseDto userDto = new UserResponseDto();
        userDto.setId(userModel.getId());
        userDto.setName(userModel.getName());
        userDto.setSurname(userModel.getSurname());
        userDto.setEmail(userModel.getEmail());
        userDto.setSerialNumber(userModel.getSerialNumber());
        userDto.setPhone(userModel.getPhone());
        userDto.setRole(userModel.getRole());
        return userDto;
    }
}
