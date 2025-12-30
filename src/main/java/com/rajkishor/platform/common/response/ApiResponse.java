package com.rajkishor.platform.common.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ApiResponse<T> {

    private boolean success;
    private String message;
    private T data;
    private LocalDateTime timestamp;
    private String path;

    public static <T> ApiResponse<T> success(String message, T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static ApiResponse<?> error(String message, String path) {
        return ApiResponse.builder()
                .success(false)
                .message(message)
                .timestamp(LocalDateTime.now())
                .path(path)
                .build();
    }
}
