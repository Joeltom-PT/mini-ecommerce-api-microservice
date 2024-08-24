package com.camcorderio.userservice.service;

import org.springframework.http.ResponseEntity;

public interface CartService {

    ResponseEntity<?> addToCart(Long productId,Long userId);

    ResponseEntity<?> getCart(Long userId);

    ResponseEntity<?> removeItemFromCart(Long userId, Long productId);
}
