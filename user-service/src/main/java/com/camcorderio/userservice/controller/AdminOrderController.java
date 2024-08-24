package com.camcorderio.userservice.controller;

import com.camcorderio.userservice.dto.UpdateOrderRequest;
import com.camcorderio.userservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminOrderController {
    private final OrderService orderService;

    @GetMapping("/getAllOrders")
    public ResponseEntity<?> getAllOrdersOfAllUsers(){
        return orderService.getAllOrdersOfAllUsers();
    }


    @PutMapping("/updateOrderStatus")
    public ResponseEntity<String> updateOrderStatus(@RequestBody UpdateOrderRequest updateOrderRequest){
        return orderService.updateOrderRequest(updateOrderRequest);
    }

}
