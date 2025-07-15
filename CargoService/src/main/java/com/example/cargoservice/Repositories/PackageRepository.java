package com.example.cargoservice.Repositories;

import com.example.cargoservice.Models.PackageModel;
import org.springframework.data.repository.CrudRepository;

public interface PackageRepository extends CrudRepository<PackageModel,String> {
}
