package com.example.userservice.Service;

import com.example.userservice.Dtos.AuthResponse;
import com.example.userservice.Dtos.LoginRequest;
import com.example.userservice.Dtos.RegisterRequestDto;
import com.example.userservice.ExceptionHandler.EmailAlreadyExistsException;
import com.example.userservice.ExceptionHandler.EmailNotFoundException;
import com.example.userservice.ExceptionHandler.InvalidCredentialsException;
import com.example.userservice.ExceptionHandler.SerialNumberAlreadyExistException;
import com.example.userservice.Extensions.Mapper;
import com.example.userservice.Extensions.Response;
import com.example.userservice.Model.UserModel;
import com.example.userservice.Repository.UserRepository;
import com.example.userservice.Service.Interface.IAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthService implements IAuthService {

    private final UserRepository userRepository;
    private final Mapper mapper;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    public AuthService(UserRepository userRepository, Mapper mapper, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public Response<AuthResponse> register(RegisterRequestDto registerRequestDto) {
        log.info("Register request has been sent {}", registerRequestDto.toString());

        UserModel userModel = mapper.toEntity(registerRequestDto);

        if(userRepository.existsByEmail((userModel.getEmail()))) {
            throw new EmailAlreadyExistsException(registerRequestDto.getEmail());
        }
        if (userRepository.existsBySerialNumber(userModel.getSerialNumber())) {
            throw new SerialNumberAlreadyExistException(userModel.getSerialNumber());
        }

        userModel.setPassword(passwordEncoder.encode(registerRequestDto.getPassword()));

        userRepository.save(userModel);
        log.info("User has been registered successfully {}", userModel);

        String token = jwtService.generateToken(userModel);
        AuthResponse authResponse= new AuthResponse(token);

        return Response.success("Kayıt başarılı",authResponse,200);
    }
    @Override
    public Response<AuthResponse> login(LoginRequest loginRequest) {
        log.info("Login request has been sent {}", loginRequest.toString());
        UserModel userModel = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new EmailNotFoundException(loginRequest.getEmail()));

        if (!passwordEncoder.matches(loginRequest.getPassword(), userModel.getPassword()))
            throw new InvalidCredentialsException("Şifre yanlış");

        String token = jwtService.generateToken(userModel);
        AuthResponse authResponse = new AuthResponse(token);
        log.info("User has been logged successfully token: {} serialnumber: {}", authResponse, userModel.getSerialNumber());
        return Response.success("Giriş başarılı", authResponse, 200);
    }

}
