package com.avilatek.ecommerce.ecommerce_api.dto;

import jakarta.validation.constraints.*;

public record RegisterRequest(
        @NotBlank(message = "El nombre de usuario es obligatorio")
        @Size(min = 4, max = 20, message = "El nombre de usuario debe tener entre 4 y 20 caracteres")
        String username,

        @NotBlank(message = "El correo electrónico es obligatorio")
        @Email(message = "El correo electrónico debe ser válido")
        String email,

        @NotBlank(message = "La contraseña es obligatoria")
        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!?*_/';:.])(?=\\S+$).{8,}$",
                message = "La contraseña debe tener más de 8 caracteres, incluyendo mayúsculas, minúsculas, números y caracteres especiales (@#$%^&+=!?*_/';:.).")
        String password
) {}
