package com.avilatek.ecommerce.ecommerce_api.util;

public class PasswordValidator {

    public static boolean isValid(String password) {
        // Mínimo 8 caracteres, 1 mayúscula, 1 minúscula, 1 número y 1 carácter especial
        String pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!?*_/';:.])(?=\\S+$).{8,}$";
        return password.matches(pattern);
    }
}