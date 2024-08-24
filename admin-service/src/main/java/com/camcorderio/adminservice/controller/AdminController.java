package com.camcorderio.adminservice.controller;

import com.camcorderio.adminservice.client.FeignClientAPIForProductService;
import com.camcorderio.adminservice.client.FeignClientAPIForUserService;
import com.camcorderio.adminservice.dto.ProductDto;
import com.camcorderio.adminservice.dto.UpdateOrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final FeignClientAPIForProductService feignClientAPIForProductService;
    private final FeignClientAPIForUserService feignClientAPIForUserService;

    @PostMapping("/addProduct")
    public ResponseEntity<String> addProduct(@Validated @RequestBody ProductDto productDto){
        return feignClientAPIForProductService.addProduct(productDto);
    }

    @PutMapping("/editProduct")
    public ResponseEntity<String> editProduct(@Validated @RequestBody ProductDto productDto){
        if (productDto.getId() == null){
            return ResponseEntity.badRequest().body("Id is missing");
        }
        return feignClientAPIForProductService.editProduct(productDto);
    }

    @GetMapping("/getAllProducts")
    public ResponseEntity<?> getAllProducts(){
        return feignClientAPIForProductService.getAllProducts();
    }

    @DeleteMapping("/deleteProduct/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") Long productId){
       return feignClientAPIForProductService.deleteProduct(productId);
    }

    @GetMapping("/getAllOrders")
    public ResponseEntity<?> getAllOrders(){
        return feignClientAPIForUserService.getAllOrdersOfAllUsers();
    }


    @PutMapping("/updateOrderStatus")
    public ResponseEntity<String> updateOrderStatus(@RequestBody UpdateOrderRequest updateOrderRequest){
        return feignClientAPIForUserService.updateOrderStatus(updateOrderRequest);
    }

}
