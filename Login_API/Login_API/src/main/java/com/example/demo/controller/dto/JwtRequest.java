package com.example.demo.controller.dto;

import lombok.Data;

@Data
public class JwtRequest {
    private String email;
    private String password;
}
