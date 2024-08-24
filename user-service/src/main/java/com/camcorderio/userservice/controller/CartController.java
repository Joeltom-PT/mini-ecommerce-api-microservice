package com.camcorderio.userservice.controller;

import com.camcorderio.userservice.service.CartService;
import com.camcorderio.userservice.service.jwt.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
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

    @GetMapping("/getCart")
    public ResponseEntity<?> getCart(HttpServletRequest request){
        String token = jwtService.getJWTFromRequest(request);
        Long userId = jwtService.getUserIdFromToken(token);
        return cartService.getCart(userId);
    }

    @PutMapping("/removeItemFromCart/{id}")
    public ResponseEntity<?> removeItemFromCart(@PathVariable("id") Long productId,HttpServletRequest request){
        String token = jwtService.getJWTFromRequest(request);
        Long userId = jwtService.getUserIdFromToken(token);
        return cartService.removeItemFromCart(userId,productId);
    }

}
