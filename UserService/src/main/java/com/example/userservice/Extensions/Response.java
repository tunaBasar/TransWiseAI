package com.example.userservice.Extensions;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.Instant;

@Data
@Schema(description = "Genel API response şeması")
public class Response<T> {

    @Schema(description = "İşlemin başarılı olup olmadığını belirtir")
    private boolean success;

    @Schema(description = "API'den dönen açıklayıcı mesaj")
    private String message;

    @Schema(description = "İşlem sonucu dönen veri")
    private T data;

    @Schema(description = "HTTP status kodu")
    private int statuscode;

    @Schema(description = "Yanıtın üretildiği zaman damgası")
    private Instant timestamp;

    public Response() {
        this.timestamp = Instant.now();
    }

    public Response(boolean success, String message, T data, int statuscode) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.statuscode = statuscode;
        this.timestamp = Instant.now();
    }

    public static <T> Response<T> success(String message, T data, int statuscode) {
        return new Response<>(true, message, data, statuscode);
    }

    public static <T> Response<T> success(T data) {
        return new Response<>(true, "İşlem başarılı", data, 200);
    }

    public static <T> Response<T> failure(String message, int statuscode) {
        return new Response<>(false, message, null, statuscode);
    }

    public static <T> Response<T> failure(String message) {
        return new Response<>(false, message, null, 500);
    }
}
