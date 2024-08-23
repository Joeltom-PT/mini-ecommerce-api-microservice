package com.camcorderio.userservice.service;

import com.camcorderio.userservice.client.FeignClientAPI;
import com.camcorderio.userservice.dto.Product;
import com.camcorderio.userservice.model.Cart;
import com.camcorderio.userservice.model.CartStatus;
import com.camcorderio.userservice.model.User;
import com.camcorderio.userservice.repository.CartRepository;
import com.camcorderio.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final FeignClientAPI feignClientAPI;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<?> orderCart(Long userId) {
        Optional<Cart> cartOptional = cartRepository.findByUserId(userId);

        if (cartOptional.isPresent()) {
            Cart cart = cartOptional.get();
            cart.setCartStatus(CartStatus.ORDERED);
            cartRepository.save(cart);

            return ResponseEntity.ok("Order placed successfully");
        } else {
            return ResponseEntity.status(404).body("Cart not found for the user");
        }
    }

    @Override
    public ResponseEntity<?> addToCart(Long productId, Long userId) {
        Product product = feignClientAPI.getProductById(productId);

        if (product == null) {
            return ResponseEntity.status(404).body("Product not found");
        }

        Optional<Cart> cartOptional = cartRepository.findByUserId(userId);
        Cart cart;

        if (cartOptional.isPresent()) {
            cart = cartOptional.get();
        } else {
            Optional<User> user = userRepository.findById(userId);

            if (!user.isPresent()) {
                return ResponseEntity.badRequest().body("User not found.");
            }

            cart = new Cart();
            cart.setUser(user.get());
            cart.setCartStatus(CartStatus.ORDERED);
            cart.setProducts(new ArrayList<>());
        }

        cart.getProducts().add(productId);
        cartRepository.save(cart);

        return ResponseEntity.ok("Product added to cart successfully");
    }

}
