package com.example.userservice.Controller;

import com.example.userservice.Dtos.UserResponseDto;
import com.example.userservice.Extensions.Response;
import com.example.userservice.Service.Interface.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserController {

    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Response<List<UserResponseDto>>> GetAllUsers() {
        log.info("GetAllUsers called");
        log.info("Authentication: {}", SecurityContextHolder.getContext().getAuthentication());

        Response<List<UserResponseDto>> userDtos = userService.GetAllUsers();
        return ResponseEntity.ok(userDtos);
    }

    @GetMapping("/{serialNumber}")
    public ResponseEntity<Response<UserResponseDto>> GetUserBySerialNumber(@PathVariable String serialNumber) {
        log.info("GetUserBySerialNumber called with serialNumber: {}", serialNumber);
        log.info("Authentication: {}", SecurityContextHolder.getContext().getAuthentication());
        log.info("Principal: {}", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        log.info("Authorities: {}", SecurityContextHolder.getContext().getAuthentication().getAuthorities());

        Response<UserResponseDto> userDto = userService.GetUserBySerialNumber(serialNumber);
        return ResponseEntity.ok(userDto);
    }
}