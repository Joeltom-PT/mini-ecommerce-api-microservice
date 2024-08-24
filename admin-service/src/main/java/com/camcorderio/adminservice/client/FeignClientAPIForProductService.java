package com.camcorderio.adminservice.client;

import com.camcorderio.adminservice.dto.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "PRODUCT-SERVICE")
public interface FeignClientAPIForProductService {

    @PostMapping("/api/product/create")
    ResponseEntity<String> addProduct(@RequestBody ProductDto productDto);

    @PutMapping("/api/product/editProduct")
    ResponseEntity<String> editProduct(@RequestBody ProductDto productDto);

    @GetMapping("/api/product/getAllProducts")
    ResponseEntity<?> getAllProducts();

    @DeleteMapping("/api/product/deleteProduct/{id}")
    ResponseEntity<String> deleteProduct(@PathVariable("id") Long productId);
}
