package com.camcorderio.userservice.controller;

import com.camcorderio.userservice.dto.UserDto;
import com.camcorderio.userservice.model.UserRoles;
import com.camcorderio.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/createUser")
    public ResponseEntity<String> createUser(@Validated @RequestBody UserDto userDto) {

        try {
            if (userService.findUserByEmail(userDto.getEmail())){
                return ResponseEntity.badRequest().body("User already exist by email. Try another mail or login!");
            }
            userDto.setUserRole(UserRoles.USER);
            userService.saveUser(userDto);
            return ResponseEntity.ok("User registration successful");
        } catch (Exception e){
            return ResponseEntity.badRequest().body("Something went wrong try again later!");
        }
    }


    @GetMapping("/login")
    public ResponseEntity<String> loginUser(@RequestParam String email,
                                            @RequestParam String password){
        return userService.loginUser(email,password);
    }

}
