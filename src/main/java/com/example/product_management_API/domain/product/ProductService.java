package com.example.product_management_API.domain.product;


import com.example.product_management_API.api.product.dto.ProductCreateRequest;
import com.example.product_management_API.api.product.dto.ProductUpdateRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository repository;


    @Transactional
    public Product create(ProductCreateRequest req) {
        Product p = Product.builder()
                .name(req.name())
                .description(req.description())
                .price(req.price())
                .stock(req.stock())
                .build();
        return repository.save(p);
    }


    @Transactional(readOnly = true)
    public Product getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found: " + id));
    }


    @Transactional(readOnly = true)
    public Page<Product> list(Pageable pageable) {
        return repository.findAll(pageable);
    }


    @Transactional
    public Product update(Long id, ProductUpdateRequest req) {
        Product p = getById(id);
        p.setName(req.name());
        p.setDescription(req.description());
        p.setPrice(req.price());
        p.setStock(req.stock());
        return repository.save(p);
    }


    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Product not found: " + id);
        }
        repository.deleteById(id);
    }
}