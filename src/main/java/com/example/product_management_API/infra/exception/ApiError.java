package com.example.product_management_API.infra.exception;


import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.OffsetDateTime;
import java.util.List;


public record ApiError(
        int status,
        String error,
        String message,
        @JsonFormat(shape = JsonFormat.Shape.STRING) OffsetDateTime timestamp,
        List<FieldError> fieldErrors
) {
    public record FieldError(String field, String message) {}
}