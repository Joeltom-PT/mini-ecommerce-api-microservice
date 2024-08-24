package com.camcorderio.userservice.controller;

import com.camcorderio.userservice.dto.AddressDto;
import com.camcorderio.userservice.service.AddressService;
import com.camcorderio.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;
    private final UserService userService;

    @PostMapping("/createAddress/{id}")
    public ResponseEntity<String> createAddress(@PathVariable("id") Long userId,
                                                @Validated @RequestBody AddressDto addressDto) throws IllegalArgumentException{
        try {
            return addressService.createAddress(userId, addressDto);
        } catch (IllegalArgumentException exception){
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @GetMapping("/listAllAddress/{id}")
    public ResponseEntity<List<AddressDto>> listAllAddress(@PathVariable("id") Long userId){
        if (!userService.findById(userId)){
            return ResponseEntity.badRequest().body(null);
        }
        List<AddressDto> addresses = addressService.findAllAddressByUserId(userId);

        return ResponseEntity.ok(addresses);
    }

    @DeleteMapping("/deleteAddress/{userId}/{addressId}")
    public ResponseEntity<String> deleteAddress(@PathVariable("userId") Long userId,
                                                @PathVariable("addressId") Long addressId){

        if (!userService.findById(userId)){
            return ResponseEntity.badRequest().body("Something went wrong!");
        }
        return addressService.deleteAddress(userId,addressId);
    }


}
