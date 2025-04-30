package com.avilatek.ecommerce.ecommerce_api.dto;

import lombok.Data;

@Data
public class AuthRequest {
    private String username;
    private String password;
}