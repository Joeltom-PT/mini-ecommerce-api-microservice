package com.camcorderio.userservice.controller;

import com.camcorderio.userservice.service.CartService;
import com.camcorderio.userservice.service.OrderService;
import com.camcorderio.userservice.service.jwt.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class OrderController {

    private final OrderService orderService;
    private final JwtService jwtService;

    @PutMapping("/order/{id}")
    public ResponseEntity<?> orderProduct(@PathVariable("id") Long addressId, HttpServletRequest request) {
        String token = jwtService.getJWTFromRequest(request);
        Long userId = jwtService.getUserIdFromToken(token);

        return orderService.orderCart(userId,addressId);
    }

    @GetMapping("/getAllOrders")
    @LoadBalanced
    public ResponseEntity<?> getAllOrders(HttpServletRequest request){
        String token = jwtService.getJWTFromRequest(request);
        Long userId = jwtService.getUserIdFromToken(token);

        return orderService.getAllOrders(userId);
    }

}
