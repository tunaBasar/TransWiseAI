package com.example.userservice.Extensions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response<T> {
    private String message;
    private T data;
    private int statusCode;
    private boolean success;

    public static <T> Response<T> success(String message, T data, int statusCode) {
        return new Response<>(message, data, statusCode, true);
    }

    public static <T> Response<T> failure(String message, int statusCode) {
        return new Response<>(message, null, statusCode, false);
    }

    // StatusCode enum kullanarak success method'u
    public static <T> Response<T> success(String message, T data, StatusCode statusCode) {
        return new Response<>(message, data, statusCode.getCode(), true);
    }

    // StatusCode enum kullanarak failure method'u
    public static <T> Response<T> failure(String message, StatusCode statusCode) {
        return new Response<>(message, null, statusCode.getCode(), false);
    }
}