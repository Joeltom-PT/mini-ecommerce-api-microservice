package com.camcorderio.userservice.service;

import com.camcorderio.userservice.client.FeignClientAPI;
import com.camcorderio.userservice.dto.CartResponseDto;
import com.camcorderio.userservice.dto.ProductDto;
import com.camcorderio.userservice.model.Cart;
import com.camcorderio.userservice.model.CartStatus;
import com.camcorderio.userservice.model.User;
import com.camcorderio.userservice.repository.CartRepository;
import com.camcorderio.userservice.repository.UserRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final FeignClientAPI feignClientAPI;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<?> addToCart(Long productId, Long userId) {
        ProductDto productDto = feignClientAPI.getProductById(productId);

        if (productDto == null) {
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
            cart.setCartStatus(CartStatus.ACTIVE);
            cart.setProducts(new ArrayList<>());
        }

        cart.getProducts().add(productId);
        cartRepository.save(cart);

        return ResponseEntity.ok("Product added to cart successfully");
    }

    public ResponseEntity<?> getCart(Long userId) {
        Optional<Cart> optionalCart = cartRepository.findByUserId(userId);
        if (optionalCart.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cart not found for user ID " + userId);
        }

        try {
            Cart cart = optionalCart.get();
            CartResponseDto cartResponseDto = CartResponseDto.builder()
                    .id(cart.getId())
                    .productDtos(takeProducts(cart.getProducts()))
                    .build();
            return ResponseEntity.ok(cartResponseDto);
        } catch (FeignException.NotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("One or more products not found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong!");
        }
    }

    @Override
    public ResponseEntity<?> removeItemFromCart(Long userId, Long productId) {
        Optional<Cart> optionalCart = cartRepository.findByUserId(userId);
        if (optionalCart.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cart not found for user ID " + userId);
        }

        Cart cart = optionalCart.get();
        boolean removed = cart.getProducts().remove(productId);

        if (removed) {
            cartRepository.save(cart);
            return ResponseEntity.ok("Product removed from cart");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found in cart");
        }
    }

    private List<ProductDto> takeProducts(List<Long> productIds) {
        return productIds.stream()
                .map(productId -> {
                    try {
                        return feignClientAPI.getProductById(productId);
                    } catch (Exception e) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

}
