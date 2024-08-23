package com.camcorderio.userservice.client;

import com.camcorderio.userservice.dto.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "PRODUCT-SERVICE")
public interface FeignClientAPI {

    @GetMapping("/api/products/getProduct/{id}")
    Product getProductById(@PathVariable("id") Long productId);
}
