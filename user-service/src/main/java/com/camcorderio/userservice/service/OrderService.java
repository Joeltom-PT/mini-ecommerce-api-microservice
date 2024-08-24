package com.camcorderio.userservice.service;

import com.camcorderio.userservice.dto.UpdateOrderRequest;
import org.springframework.http.ResponseEntity;

public interface OrderService {
    ResponseEntity<?> orderCart(Long userId, Long addressId);

    ResponseEntity<?> getAllOrders(Long userId);

    ResponseEntity<?> getAllOrdersOfAllUsers();

    ResponseEntity<String> updateOrderRequest(UpdateOrderRequest updateOrderRequest);
}
