package com.example.cargoservice.Dtos;

import lombok.Data;

@Data
public class ResponsePackage {

    public String cargoId;
    public Double cargoWeight;
    public boolean isFragile;
    public boolean isPerishable;
}
