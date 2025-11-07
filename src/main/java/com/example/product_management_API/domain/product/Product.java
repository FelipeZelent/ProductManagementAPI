package com.example.product_management_API.domain.product;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


import java.math.BigDecimal;
import java.time.OffsetDateTime;


@Entity
@Table(name = "products")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Product {


    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotBlank
    @Column(nullable = false)
    private String name;


    @Size(max = 500)
    private String description;


    @NotNull
    @DecimalMin(value = "0.0", inclusive = true)
    @Digits(integer = 12, fraction = 2)
    @Column(nullable = false, precision = 14, scale = 2)
    private BigDecimal price;


    @NotNull
    @Min(0)
    @Column(nullable = false)
    private Integer stock;


    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;


    @UpdateTimestamp
    private OffsetDateTime updatedAt;
}