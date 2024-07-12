package com.example.taskmanagement.services;

import com.example.taskmanagement.config.JwtGenerator;
import com.example.taskmanagement.models.Role;
import com.example.taskmanagement.models.User;
import com.example.taskmanagement.models.request.LoginRequest;
import com.example.taskmanagement.models.request.RegisterRequest;
import com.example.taskmanagement.models.response.LoginResponse;
import com.example.taskmanagement.repositories.RoleRepository;
import com.example.taskmanagement.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private JwtGenerator jwtGenerator;

    @Autowired
    public AuthService(AuthenticationManager authenticationManager, UserRepository userRepository,
                       RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtGenerator jwtGenerator) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator = jwtGenerator;
    }
    public ResponseEntity<LoginResponse> login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Map<String, Object> additionalClaims = new HashMap<>();
        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        additionalClaims.put("firstname", user.getFirstName());
        additionalClaims.put("lastname", user.getLastName());
        String token = jwtGenerator.generateToken(authentication, additionalClaims);
        return new ResponseEntity<>(new LoginResponse(token), HttpStatus.OK);
    }

    public ResponseEntity<String> register(RegisterRequest registerDto) {
        if (registerDto.getUsername().isEmpty() ||
                registerDto.getPassword().isEmpty() ||
                registerDto.getFirstName().isEmpty() ||
                registerDto.getLastName().isEmpty()) {
            return new ResponseEntity<>("All fields must have a valid length!", HttpStatus.BAD_REQUEST);
        }
        if (userRepository.existsByUsername(registerDto.getUsername())) {
            return new ResponseEntity<>("Username is taken!", HttpStatus.BAD_REQUEST);
        }
        User user = new User();
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode((registerDto.getPassword())));
        user.setFirstName((registerDto.getFirstName()));
        user.setLastName((registerDto.getLastName()));
        Role roles = roleRepository.findByName("USER").get();
        user.setRoles(Collections.singletonList(roles));

        userRepository.save(user);

        return new ResponseEntity<>("User registered successfully!", HttpStatus.OK);
    }
    public ResponseEntity<String> verifyToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return new ResponseEntity<>("Valid token!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid token!", HttpStatus.UNAUTHORIZED);
        }
    }

    public String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
                return principal.toString();
        }
    }
}
