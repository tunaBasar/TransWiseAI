package com.example.userservice.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Data
@RedisHash("User")
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {

    @Id
    private String id;

    private String name;
    private String surname;

    @Indexed
    private String serialNumber;

    @Indexed
    private String email;
    private String password;
    private String phone;
    private Role role;
}



