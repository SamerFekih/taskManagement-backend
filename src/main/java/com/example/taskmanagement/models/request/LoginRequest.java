package com.example.taskmanagement.models.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}