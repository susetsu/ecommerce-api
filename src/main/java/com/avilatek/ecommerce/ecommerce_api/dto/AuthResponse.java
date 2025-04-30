package com.avilatek.ecommerce.ecommerce_api.dto;

import lombok.Data;

@Data
public class AuthResponse {
    private String token;
    private String email;
    private Long id;
}