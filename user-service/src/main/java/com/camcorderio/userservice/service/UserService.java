package com.camcorderio.userservice.service;

import com.camcorderio.userservice.dto.UserDto;
import org.springframework.http.ResponseEntity;

public interface UserService {
    boolean findUserByEmail(String email);

    void saveUser(UserDto userDto);


    ResponseEntity<String> loginUser(String email, String password);
}
