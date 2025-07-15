package com.example.cargoservice.Models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Data
@RedisHash("Package")
public class PackageModel {

    @Id
    public String id;

    public String cargoId;
    public Double cargoWeight;
    public boolean isFragile;
    public boolean isPerishable;
}
