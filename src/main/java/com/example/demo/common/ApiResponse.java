package com.example.demo.common;

public class ApiResponse<T> {

    private boolean success;
    private int statusCode;
    private String message;
    private T data;

    public ApiResponse(boolean success, int statusCode,
                       String message, T data) {
        this.success = success;
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }

    // Success response
    public static <T> ApiResponse<T> success(
            int statusCode, String message, T data) {

        return new ApiResponse<>(
                true, statusCode, message, data
        );
    }

    // Error response
    public static <T> ApiResponse<T> error(
            int statusCode, String message) {

        return new ApiResponse<>(
                false, statusCode, message, null
        );
    }

    // Getters and Setters

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}