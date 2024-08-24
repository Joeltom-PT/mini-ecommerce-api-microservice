package com.camcorderio.userservice.service;

import com.camcorderio.userservice.dto.AddressDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AddressService {
    ResponseEntity<String> createAddress(Long userId, AddressDto addressDto) throws IllegalArgumentException;


    List<AddressDto> findAllAddressByUserId(Long userId);

    ResponseEntity<String> deleteAddress(Long userId, Long addressId);
}
