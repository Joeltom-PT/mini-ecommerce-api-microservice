package com.camcorderio.productservice.service;

import com.camcorderio.productservice.dto.ProductDto;
import com.camcorderio.productservice.model.Product;
import com.camcorderio.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

    @Override
    public ResponseEntity<String> createProduct(ProductDto productDto) {
        try {
            Product product = Product.builder()
                    .name(productDto.getName())
                    .description(productDto.getDescription())
                    .price(productDto.getPrice())
                    .stock(productDto.getStock())
                    .build();
            productRepository.save(product);
            return ResponseEntity.ok("Product creation successful!");
        } catch (Exception e){
         return ResponseEntity.badRequest().body("Something went wrong. Try again!");
        }
    }

    @Override
    public ResponseEntity<?> getProductById(Long productId) {
        if (productRepository.existsById(productId)){
           Product product = productRepository.getReferenceById(productId);
           ProductDto productDto = ProductDto.builder()
                   .id(product.getId())
                   .name(product.getName())
                   .description(product.getDescription())
                   .price(product.getPrice())
                   .stock(product.getStock())
                   .build();
           return ResponseEntity.ok(productDto);
        }
        return ResponseEntity.badRequest().body("Product not find by id!");
    }

    @Override
    public ResponseEntity<?> getAllProducts() {
        List<ProductDto> productDtoList = productRepository.findAll().stream()
                .map(product -> ProductDto.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .description(product.getDescription())
                        .price(product.getPrice())
                        .stock(product.getStock())
                        .build())
                .collect(Collectors.toList());

        if (productDtoList.isEmpty()){
            return ResponseEntity.badRequest().body("Products not found!");
        }
        return ResponseEntity.ok(productDtoList);
    }



}
