package com.camcorderio.productservice.controller;

import com.camcorderio.productservice.dto.ProductDto;
import com.camcorderio.productservice.model.Product;
import com.camcorderio.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping("/create")
    @LoadBalanced
    public ResponseEntity<String> addProduct(@RequestBody ProductDto productDto){
        return productService.createProduct(productDto);
    }

    @PutMapping("/editProduct")
    @LoadBalanced
    public ResponseEntity<String> editProduct(@RequestBody ProductDto productDto){
        return productService.editProduct(productDto);
    }

    @GetMapping("/getProduct/{id}")
    @LoadBalanced
    public ResponseEntity<?> getProduct(@PathVariable("id") Long productId){
        return productService.getProductById(productId);
    }

    @GetMapping("/getAllProducts")
    @LoadBalanced
    public ResponseEntity<?> getAllProducts(){
        return productService.getAllProducts();
    }

    @DeleteMapping("/deleteProduct/{id}")
    @LoadBalanced
    public ResponseEntity<String> deleteProduct(@PathVariable("id") Long productId){
        return productService.deleteProduct(productId);
    }

}
