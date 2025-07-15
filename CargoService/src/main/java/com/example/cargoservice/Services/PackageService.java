package com.example.cargoservice.Services;

import com.example.cargoservice.Dtos.CreatePackageDto;
import com.example.cargoservice.Dtos.ResponsePackage;
import com.example.cargoservice.Extensions.Mapper;
import com.example.cargoservice.Extensions.Response;
import com.example.cargoservice.Extensions.StatusCode;
import com.example.cargoservice.Models.PackageModel;
import com.example.cargoservice.Repositories.PackageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PackageService implements IPackageService {

    private static final Logger logger = LoggerFactory.getLogger(PackageService.class);
    private final PackageRepository packageRepository;
    private final Mapper mapper;

    // Maksimum kargo ağırlığı (kg)
    private static final Double MAX_CARGO_WEIGHT = 1000.0;
    private static final Double MIN_CARGO_WEIGHT = 0.1;

    public PackageService(PackageRepository packageRepository, Mapper mapper) {
        this.packageRepository = packageRepository;
        this.mapper = mapper;
    }

    public Response<ResponsePackage> createPackage(CreatePackageDto createPackageDto) {
        logger.info("Package creation request received");

        try {
            if (createPackageDto == null) {
                logger.error("CreatePackageDto is null");
                return Response.failure("Package bilgileri boş olamaz", StatusCode.BAD_REQUEST);
            }

            if (createPackageDto.cargoWeight == null) {
                logger.error("Cargo weight is null");
                return Response.failure("Kargo ağırlığı belirtilmelidir", StatusCode.BAD_REQUEST);
            }

            if (createPackageDto.cargoWeight <= 0) {
                logger.error("Invalid cargo weight: {}", createPackageDto.cargoWeight);
                return Response.failure("Kargo ağırlığı 0'dan büyük olmalıdır", StatusCode.BAD_REQUEST);
            }

            if (createPackageDto.cargoWeight < MIN_CARGO_WEIGHT) {
                logger.error("Cargo weight too small: {}", createPackageDto.cargoWeight);
                return Response.failure("Minimum kargo ağırlığı " + MIN_CARGO_WEIGHT + " kg olmalıdır", StatusCode.BAD_REQUEST);
            }

            if (createPackageDto.cargoWeight > MAX_CARGO_WEIGHT) {
                logger.error("Cargo weight exceeds maximum limit: {}", createPackageDto.cargoWeight);
                return Response.failure("Maksimum kargo ağırlığı " + MAX_CARGO_WEIGHT + " kg'ı geçemez", StatusCode.BAD_REQUEST);
            }

            // Kırılgan ve bozulabilir kargo özel kontrolleri
            if (createPackageDto.isFragile && createPackageDto.cargoWeight > 50.0) {
                logger.warn("Heavy fragile package detected: {} kg", createPackageDto.cargoWeight);
            }

            if (createPackageDto.isPerishable && createPackageDto.cargoWeight > 100.0) {
                logger.warn("Heavy perishable package detected: {} kg", createPackageDto.cargoWeight);
            }

            logger.info("Creating package - Weight: {} kg, Fragile: {}, Perishable: {}",
                    createPackageDto.cargoWeight,
                    createPackageDto.isFragile,
                    createPackageDto.isPerishable);

            PackageModel packageModel;
            try {
                packageModel = mapper.toEntity(createPackageDto);
                logger.debug("Package mapped to entity successfully");
            } catch (Exception e) {
                logger.error("Error mapping CreatePackageDto to PackageModel", e);
                return Response.failure("Kargo bilgileri işlenirken hata oluştu", StatusCode.INTERNAL_SERVER_ERROR);
            }

            try {
                packageRepository.save(packageModel);
                logger.info("Package saved to database successfully with ID: {}", packageModel.getId());
            } catch (Exception e) {
                logger.error("Error saving package to database", e);
                return Response.failure("Kargo kaydedilirken bir hata oluştu", StatusCode.INTERNAL_SERVER_ERROR);
            }

            ResponsePackage responsePackage;
            try {
                responsePackage = mapper.toDto(packageModel);
                logger.debug("Package mapped to response DTO successfully");
            } catch (Exception e) {
                logger.error("Error mapping PackageModel to ResponsePackage", e);
                return Response.failure("Yanıt hazırlanırken hata oluştu", StatusCode.INTERNAL_SERVER_ERROR);
            }

            logger.info("Package created successfully with ID: {}", packageModel.getId());
            return Response.success("Kargo başarıyla oluşturuldu", responsePackage, StatusCode.CREATED);

        } catch (Exception e) {
            logger.error("Unexpected error occurred while creating package", e);
            return Response.failure("Beklenmeyen bir hata oluştu", StatusCode.INTERNAL_SERVER_ERROR);
        }
    }
}