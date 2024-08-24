package com.camcorderio.adminservice.client;

import com.camcorderio.adminservice.dto.ProductDto;
import com.camcorderio.adminservice.dto.UpdateOrderRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "USER-SERVICE")
public interface FeignClientAPIForUserService {

    @GetMapping("/api/admin/getAllOrders")
    ResponseEntity<?> getAllOrdersOfAllUsers();

    @PutMapping("/api/admin/updateOrderStatus")
    ResponseEntity<String> updateOrderStatus(@RequestBody UpdateOrderRequest updateOrderRequest);
}
