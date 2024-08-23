package com.camcorderio.userservice.controller;

import com.camcorderio.userservice.service.CartService;
import com.camcorderio.userservice.service.jwt.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final JwtService jwtService;
    @PostMapping("/addToCart/{id}")
    public ResponseEntity<?> addToCart(@PathVariable("id") Long productId, HttpServletRequest request){
        String token = jwtService.getJWTFromRequest(request);
        Long userId = jwtService.getUserIdFromToken(token);
        return cartService.addToCart(productId,userId);
    }

}
