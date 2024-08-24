package com.camcorderio.userservice.service;

import com.camcorderio.userservice.client.FeignClientAPI;
import com.camcorderio.userservice.dto.ProductDto;
import com.camcorderio.userservice.dto.UpdateOrderRequest;
import com.camcorderio.userservice.model.*;
import com.camcorderio.userservice.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final FeignClientAPI feignClientAPI;
    private final OrderRepository orderRepository;
    private final AddressRepository addressRepository;
    private final OrderItemRepository orderItemRepository;

    public ResponseEntity<?> orderCart(Long userId,Long addressId){
        Optional<Cart> cart = cartRepository.findByUserId(userId);

        Optional<Address> address = addressRepository.findByUserIdAndId(userId,addressId);

        if (!cart.isPresent() || cart.get().getProducts().isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cart is empty or does not exist.");
        }


        if (!address.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Address is not present.");
        }

        int totalAmount = 0;

        List<OrderItem> orderItems = new ArrayList<>();

        for (Long productId : cart.get().getProducts()){
            ProductDto productDto = feignClientAPI.getProductById(productId);
            if (productDto == null || productDto.getStock() <= 0){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product not available or out of stock.");
            }
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(productId);
            orderItem.setQuantity(1);
            orderItem.setPrice(productDto.getPrice());
            orderItems.add(orderItem);
            totalAmount += productDto.getPrice();
        }

        Order order = new Order();
        order.setUser(userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found")));
        order.setOrderItems(orderItems);
        order.setOrderStatus(OrderStatus.PENDING);
        order.setAddressId(addressId);
        order.setTotalAmount(totalAmount);
        orderRepository.save(order);

        for (OrderItem orderItem : orderItems){
            orderItem.setOrder(order);
            orderItemRepository.save(orderItem);
        }

        cart.get().setProducts(new ArrayList<>());
        cartRepository.save(cart.get());

        return ResponseEntity.ok("Order placed successfully. Order ID: " + order.getId());
    }

    @Override
    public ResponseEntity<?> getAllOrders(Long userId) {
        List<Order> orders = orderRepository.findAllByUserId(userId);
        if (orders.isEmpty()){
            return ResponseEntity.badRequest().body("Orders not found");
        }
        return ResponseEntity.ok(orders);
    }

    @Override
    public ResponseEntity<?> getAllOrdersOfAllUsers() {
        List<Order> orders = orderRepository.findAll();
        if (orders.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(orders);
    }

    @Override
    public ResponseEntity<String> updateOrderRequest(UpdateOrderRequest updateOrderRequest) {
        Optional<Order> order = orderRepository.findById(updateOrderRequest.getOrderId());
        if (!order.isPresent()) {
            return ResponseEntity.ok("Order not found by id.");
        }

        try {
            String status = updateOrderRequest.getStatus();
            if ("SHIPPED".equals(status)) {
                order.get().setOrderStatus(OrderStatus.SHIPPED);
            } else if ("DELIVERED".equals(status)) {
                order.get().setOrderStatus(OrderStatus.DELIVERED);
            } else if ("PENDING".equals(status)) {
                order.get().setOrderStatus(OrderStatus.PENDING);
            } else {
                return ResponseEntity.ok("Order status is invalid.");
            }
            orderRepository.save(order.get());
            return ResponseEntity.ok("Order status updated.");
        } catch (Exception e) {
            return ResponseEntity.ok("Something went wrong. Try again!");
        }
    }

}
