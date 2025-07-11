package com.example.userservice.Repository;

import com.example.userservice.Model.UserModel;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<UserModel, String> {
    Optional<UserModel> findByEmail(String email);

    Optional<UserModel> findBySerialNumber(String serialNumber);

    boolean existsByEmail(String email);

    boolean existsBySerialNumber(String serialNumber);
}
