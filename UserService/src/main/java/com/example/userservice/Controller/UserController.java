package com.example.userservice.Controller;

import com.example.userservice.Dtos.UserResponseDto;
import com.example.userservice.Extensions.Response;
import com.example.userservice.Service.Interface.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final IUserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Response<List<UserResponseDto>>> GetAllUsers() {
        logger.info("GetAllUsers called");
        logger.info("Authentication: {}", SecurityContextHolder.getContext().getAuthentication());

        Response<List<UserResponseDto>> userDtos = userService.GetAllUsers();
        return ResponseEntity.ok(userDtos);
    }

    @GetMapping("/{serialNumber}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Response<UserResponseDto>> GetUserBySerialNumber(@PathVariable String serialNumber) {
        logger.info("GetUserBySerialNumber called with serialNumber: {}", serialNumber);
        logger.info("Authentication: {}", SecurityContextHolder.getContext().getAuthentication());
        logger.info("Principal: {}", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        logger.info("Authorities: {}", SecurityContextHolder.getContext().getAuthentication().getAuthorities());

        Response<UserResponseDto> userDto = userService.GetUserBySerialNumber(serialNumber);
        return ResponseEntity.ok(userDto);
    }

    // Kullanıcının kendi profilini görebilmesi için
    @GetMapping("/profile")
    @PreAuthorize("hasRole('Client') or hasRole('Driver') or hasRole('Admin')")
    public ResponseEntity<Response<UserResponseDto>> GetMyProfile() {
        logger.info("GetMyProfile called");
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        logger.info("Current user email: {}", currentUserEmail);

        // Bu method için UserService'e yeni bir method eklemeniz gerekecek
        // Response<UserResponseDto> userDto = userService.GetUserByEmail(currentUserEmail);
        // return ResponseEntity.ok(userDto);

        return ResponseEntity.ok(Response.success("Profil bilgisi", null, 200));
    }
}