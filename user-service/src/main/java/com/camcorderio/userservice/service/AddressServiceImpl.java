package com.camcorderio.userservice.service;

import com.camcorderio.userservice.dto.AddressDto;
import com.camcorderio.userservice.model.Address;
import com.camcorderio.userservice.model.User;
import com.camcorderio.userservice.repository.AddressRepository;
import com.camcorderio.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final UserService userService;
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    @Override
    public ResponseEntity<String> createAddress(Long userId, AddressDto addressDto) throws IllegalArgumentException {

        if (!userService.findById(userId)) {
            return ResponseEntity.badRequest().body("User not found. Try again!");
        }

        User user = userRepository.findById(userId).orElseThrow(() ->
                new IllegalStateException("User not found."));

        Address address = Address.builder()
                .address(addressDto.getAddress())
                .post(addressDto.getPost())
                .user(user)
                .build();

        Address savedAddress = addressRepository.save(address);

        user.getAddresses().add(savedAddress);

        userRepository.save(user);

        return ResponseEntity.ok("Address created successfully!");
    }

    public List<AddressDto> findAllAddressByUserId(Long userId) {
        List<Address> addresses = addressRepository.findByUserId(userId);
        return addresses.stream()
                .map(address -> new AddressDto(address.getId(), address.getAddress(), address.getPost()))
                .collect(Collectors.toList());
    }


}
