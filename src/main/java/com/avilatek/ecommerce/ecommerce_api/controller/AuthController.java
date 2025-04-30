package com.avilatek.ecommerce.ecommerce_api.controller;

import com.avilatek.ecommerce.ecommerce_api.dto.AuthRequest;
import com.avilatek.ecommerce.ecommerce_api.dto.AuthResponse;
import com.avilatek.ecommerce.ecommerce_api.dto.RegisterRequest;
import com.avilatek.ecommerce.ecommerce_api.model.User;
import com.avilatek.ecommerce.ecommerce_api.service.UserService;
import com.avilatek.ecommerce.ecommerce_api.util.EmailValidate;
import com.avilatek.ecommerce.ecommerce_api.util.JwtUtil;
import com.avilatek.ecommerce.ecommerce_api.util.PasswordValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, UserService userService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @Operation(summary = "Inicio de sesión de usuario",
            description = "Autentica a un usuario con su nombre de usuario y contraseña, y devuelve un token JWT para futuras solicitudes.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autenticado exitosamente",
                    content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = AuthResponse.class))),
            @ApiResponse(responseCode = "401", description = "Credenciales inválidas")
    })
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Parameter(description = "Detalles de autenticación que incluyen nombre de usuario y contraseña")
            @RequestBody AuthRequest authRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = (User) authentication.getPrincipal();
        String jwtToken = jwtUtil.generateToken(user);

        AuthResponse response = new AuthResponse();
        response.setToken(jwtToken);
        response.setId(user.getId());
        response.setEmail(user.getEmail());

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Registro de usuario",
            description = "Registra un nuevo usuario proporcionando nombre de usuario, correo electrónico y contraseña. Se realizan validaciones del formato del correo y la fortaleza de la contraseña.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario registrado exitosamente",
                    content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = User.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos (por ejemplo, correo inválido o contraseña débil)"),
            @ApiResponse(responseCode = "409", description = "El nombre de usuario o correo electrónico ya existe")
    })
    @PostMapping("/register")
    public ResponseEntity<User> register(
            @Parameter(description = "Cuerpo de la solicitud que contiene nombre de usuario, correo electrónico y contraseña para el registro")
            @Valid @RequestBody RegisterRequest request) {

        // Validación adicional del correo electrónico
        if (!EmailValidate.isValid(request.email())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Formato de correo electrónico inválido");
        }

        // Validación adicional de la contraseña
        if (!PasswordValidator.isValid(request.password())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La contraseña debe contener: mínimo 8 caracteres, 1 mayúscula, 1 minúscula, 1 número y 1 carácter especial"
            );
        }

        User user = new User();
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setPassword(request.password());

        User registeredUser = userService.registerUser(user);
        return ResponseEntity.ok(registeredUser);
    }
}
