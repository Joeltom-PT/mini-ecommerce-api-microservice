package com.camcorderio.userservice.service;

import com.camcorderio.userservice.dto.UserDto;
import com.camcorderio.userservice.mapper.UserMapper;
import com.camcorderio.userservice.model.User;
import com.camcorderio.userservice.repository.UserRepository;
import com.camcorderio.userservice.service.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public boolean findUserByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    @Override
    public void saveUser(UserDto userDto) {
        User user = UserMapper.mapToUser(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public ResponseEntity<String> loginUser(String email, String password) {

        if (!isEmailExist(email)) {
            return new ResponseEntity<>("Email Not Exist", HttpStatus.BAD_REQUEST);
        }
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(email, password));
            final String jwtToken = "Bearer- " + jwtService.generateToken(email);

            return new ResponseEntity<>(jwtToken, HttpStatus.OK);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            return new ResponseEntity<>("Invalid Credential", HttpStatus.UNAUTHORIZED);
        }
    }

    public boolean isEmailExist(String email) {
        return userRepository.existsByEmail(email);
    }

}
