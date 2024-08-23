package com.camcorderio.productservice.controller;

import com.camcorderio.productservice.dto.ProductDto;
import com.camcorderio.productservice.model.Product;
import com.camcorderio.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<String> addProduct(@Validated @RequestBody ProductDto productDto){
        return productService.createProduct(productDto);
    }

    @GetMapping("/getProduct/{id}")
    public ResponseEntity<?> getProduct(@PathVariable("id") Long productId){
        return productService.getProductById(productId);
    }

    @GetMapping("/getAllProducts")
    public ResponseEntity<?> getAllProducts(){
        return productService.getAllProducts();
    }

}
