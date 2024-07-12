package com.example.taskmanagement.controllers;
import com.example.taskmanagement.models.response.LoginResponse;
import com.example.taskmanagement.models.request.LoginRequest;
import com.example.taskmanagement.models.request.RegisterRequest;
import com.example.taskmanagement.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Authenticate a user and generate a JWT token.
     *
     * @param loginRequest the login request containing username and password
     * @return a response entity containing the JWT token
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){

        return authService.login(loginRequest);
    }
    /**
     * Register a new user.
     *
     * @param registerRequest the register request containing user details
     * @return a response entity with the registration status
     */
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        return authService.register(registerRequest);
    }

    /**
     * Verify if the current token is valid.
     *
     * @return a response entity indicating the token validity
     */
    @PostMapping("/verifyToken")
    public ResponseEntity<String> verifyToken() {
        return authService.verifyToken();
    }
}