package com.camcorderio.userservice.service;

import org.springframework.http.ResponseEntity;

public interface CartService {

    ResponseEntity<?> orderCart(Long userId);

    ResponseEntity<?> addToCart(Long productId,Long userId);
}
