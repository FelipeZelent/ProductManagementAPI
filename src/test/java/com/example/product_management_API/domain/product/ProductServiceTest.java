package com.example.product_management_API.domain.product;

import com.example.product_management_API.api.product.dto.ProductCreateRequest;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private ProductService service;

    @Test
    void create_ShouldSaveProduct_WhenNameIsUnique() {
        // Arrange
        ProductCreateRequest req = new ProductCreateRequest("Product 1", "Desc", BigDecimal.TEN, 10);
        when(repository.existsByNameIgnoreCase(req.name())).thenReturn(false);
        when(repository.save(any(Product.class))).thenAnswer(i -> {
            Product p = i.getArgument(0);
            p.setId(1L);
            return p;
        });

        // Act
        Product created = service.create(req);

        // Assert
        assertNotNull(created.getId());
        assertEquals("Product 1", created.getName());
        verify(repository).save(any(Product.class));
    }

    @Test
    void create_ShouldThrowException_WhenNameExists() {
        // Arrange
        ProductCreateRequest req = new ProductCreateRequest("Product 1", "Desc", BigDecimal.TEN, 10);
        when(repository.existsByNameIgnoreCase(req.name())).thenReturn(true);

        // Act & Assert
        assertThrows(EntityExistsException.class, () -> service.create(req));
        verify(repository, never()).save(any());
    }

    @Test
    void getById_ShouldReturnProduct_WhenExists() {
        // Arrange
        Product product = Product.builder().id(1L).name("P1").build();
        when(repository.findById(1L)).thenReturn(Optional.of(product));

        // Act
        Product result = service.getById(1L);

        // Assert
        assertEquals(1L, result.getId());
    }

    @Test
    void getById_ShouldThrowException_WhenNotFound() {
        // Arrange
        when(repository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> service.getById(1L));
    }
}
