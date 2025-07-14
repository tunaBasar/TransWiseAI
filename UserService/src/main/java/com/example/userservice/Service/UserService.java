package com.example.userservice.Service;

import com.example.userservice.Dtos.UserResponseDto;
import com.example.userservice.Extensions.Mapper;
import com.example.userservice.Extensions.Response;
import com.example.userservice.Model.UserModel;
import com.example.userservice.Repository.UserRepository;
import com.example.userservice.Service.Interface.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

@Service
@Slf4j
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final Mapper mapper;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService(UserRepository userRepository, Mapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public Response<List<UserResponseDto>> GetAllUsers() {
        logger.info("GetAllUsers called");
        try {
            List<UserResponseDto> userDtos = StreamSupport.stream(userRepository.findAll().spliterator(), false)
                    .map(mapper::toResponseDto)
                    .collect(toList());

            logger.info("Found {} users", userDtos.size());
            return Response.success("Bütün kullanıcılar geldi", userDtos, 200);
        } catch (Exception e) {
            logger.error("Error in GetAllUsers", e);
            return Response.failure("Kullanıcılar getirilirken hata oluştu", 500);
        }
    }

    @Override
    public Response<UserResponseDto> GetUserBySerialNumber(String serialNumber) {
        logger.info("GetUserBySerialNumber called with serialNumber: {}", serialNumber);

        try {
            Optional<UserModel> userOptional = userRepository.findBySerialNumber(serialNumber);

            if (userOptional.isEmpty()) {
                logger.warn("User not found with serialNumber: {}", serialNumber);
                return Response.failure("Bu TC'ye ait kullanıcı bulunamadı", 404);
            }

            UserModel user = userOptional.get();
            logger.info("User found: {}", user.getEmail());

            UserResponseDto userResponseDto = mapper.toResponseDto(user);
            return Response.success("Kayıt bulundu TC No: " + serialNumber, userResponseDto, 200);

        } catch (Exception e) {
            logger.error("Error in GetUserBySerialNumber", e);
            return Response.failure("Kullanıcı getirilirken hata oluştu", 500);
        }
    }
}