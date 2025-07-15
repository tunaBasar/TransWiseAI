package com.example.cargoservice.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.security.Timestamp;

@Data
@RedisHash("Cargo")
@AllArgsConstructor
@NoArgsConstructor
public class CargoModel {

    @Id
    public String Id;
    public String trackingNumber;
    public String senderId;
    public String senderName;
    public String receiverName;
    public String receiverPhone;
    public String pickupAddress;
    public String deliveryAddress;
    public Timestamp pickupTime;
    public Timestamp deliveryTime;
    public Status status;
    public String routeId;

    public Timestamp createdAt;
    public Timestamp updatedAt;


}
