package com.example.product_management_API.api.product.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record ProductUpdateRequest(
        @NotBlank String name,
        @Size(max = 500) String description,
        @NotNull @DecimalMin("0.0") @Digits(integer = 12, fraction = 2) BigDecimal price,
        @NotNull @Min(0) Integer stock
) {}