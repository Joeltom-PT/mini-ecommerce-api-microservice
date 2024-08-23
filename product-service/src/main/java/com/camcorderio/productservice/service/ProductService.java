package com.camcorderio.productservice.service;

import com.camcorderio.productservice.dto.ProductDto;
import org.springframework.http.ResponseEntity;

public interface ProductService {
    ResponseEntity<String> createProduct(ProductDto productDto);

    ResponseEntity<?> getProductById(Long productId);

    ResponseEntity<?> getAllProducts();
}
