package com.camcorderio.userservice.controller;

import com.camcorderio.userservice.service.CartService;
import com.camcorderio.userservice.service.jwt.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class OrderController {

    private final CartService cartService;
    private final JwtService jwtService;

    @PutMapping("/order")
    public ResponseEntity<?> orderProduct(HttpServletRequest request) {
        String token = jwtService.getJWTFromRequest(request);
        Long userId = jwtService.getUserIdFromToken(token);

        return cartService.orderCart(userId);
    }
}
