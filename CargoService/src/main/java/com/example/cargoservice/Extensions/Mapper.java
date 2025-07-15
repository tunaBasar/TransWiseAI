package com.example.cargoservice.Extensions;

import com.example.cargoservice.Dtos.CreatePackageDto;
import com.example.cargoservice.Dtos.ResponsePackage;
import com.example.cargoservice.Models.PackageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
public class Mapper {

    private static final Logger logger = LoggerFactory.getLogger(Mapper.class);

    public PackageModel toEntity(CreatePackageDto createPackageDto) {
        logger.debug("Converting CreatePackageDto to PackageModel");

        if (createPackageDto == null) {
            logger.error("CreatePackageDto is null in mapper");
            throw new IllegalArgumentException("CreatePackageDto cannot be null");
        }

        try {
            PackageModel packageModel = new PackageModel();

            String cargoId = generateCargoId();
            packageModel.cargoId = cargoId;

            packageModel.cargoWeight = createPackageDto.cargoWeight;
            packageModel.isFragile = createPackageDto.isFragile;
            packageModel.isPerishable = createPackageDto.isPerishable;

            logger.debug("PackageModel created with cargoId: {}", cargoId);
            return packageModel;

        } catch (Exception e) {
            logger.error("Error converting CreatePackageDto to PackageModel", e);
            throw new RuntimeException("Mapping error occurred", e);
        }
    }

    public ResponsePackage toDto(PackageModel packageModel) {
        logger.debug("Converting PackageModel to ResponsePackage");

        if (packageModel == null) {
            logger.error("PackageModel is null in mapper");
            throw new IllegalArgumentException("PackageModel cannot be null");
        }

        try {
            ResponsePackage responsePackage = new ResponsePackage();

            responsePackage.cargoId = packageModel.cargoId;
            responsePackage.cargoWeight = packageModel.cargoWeight;
            responsePackage.isFragile = packageModel.isFragile;
            responsePackage.isPerishable = packageModel.isPerishable;

            logger.debug("ResponsePackage created for cargoId: {}", packageModel.cargoId);
            return responsePackage;

        } catch (Exception e) {
            logger.error("Error converting PackageModel to ResponsePackage", e);
            throw new RuntimeException("Mapping error occurred", e);
        }
    }

    private String generateCargoId() {

        String datePrefix = java.time.LocalDate.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd"));
        String randomSuffix = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        return String.format("CARGO-%s-%s", datePrefix, randomSuffix);
    }
}