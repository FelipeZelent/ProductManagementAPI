package com.example.product_management_API.api.product;


import com.example.product_management_API.api.product.dto.ProductCreateRequest;
import com.example.product_management_API.api.product.dto.ProductUpdateRequest;
import com.example.product_management_API.domain.product.Product;
import com.example.product_management_API.domain.product.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {


    private final ProductService service;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(@RequestBody @Valid ProductCreateRequest req) {
        return service.create(req);
    }


    @GetMapping("/{id}")
    public Product get(@PathVariable Long id) {
        return service.getById(id);
    }


    @GetMapping
    public Page<Product> list(Pageable pageable) {
        return service.list(pageable);
    }


    @PutMapping("/{id}")
    public Product update(@PathVariable Long id, @RequestBody @Valid ProductUpdateRequest req) {
        return service.update(id, req);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}