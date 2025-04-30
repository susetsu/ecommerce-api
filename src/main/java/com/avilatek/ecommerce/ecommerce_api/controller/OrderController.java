package com.avilatek.ecommerce.ecommerce_api.controller;

import com.avilatek.ecommerce.ecommerce_api.dto.OrderDto;
import com.avilatek.ecommerce.ecommerce_api.dto.OrderResponseDto;
import com.avilatek.ecommerce.ecommerce_api.dto.PaginatedOrderResponse;
import com.avilatek.ecommerce.ecommerce_api.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Operation(summary = "Crear un nuevo pedido",
            description = "Crea un nuevo pedido basado en los datos proporcionados por el cliente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido creado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content)
    })
    @PostMapping
    public ResponseEntity<Map<String, String>> createOrder(
            @RequestBody(description = "Datos del pedido", required = true) OrderDto orderDto) {
        orderService.createOrder(orderDto);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Order created");
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Obtener pedidos del usuario",
            description = "Obtiene una lista paginada de los pedidos realizados por el usuario autenticado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de pedidos obtenida exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PaginatedOrderResponse.class))),
            @ApiResponse(responseCode = "401", description = "No autorizado", content = @Content)
    })
    @GetMapping
    public ResponseEntity<PaginatedOrderResponse> getUserOrders(Pageable pageable) {
        return ResponseEntity.ok(orderService.getUserOrders(pageable));
    }

    @Operation(summary = "Obtener un pedido por ID",
            description = "Obtiene los detalles de un pedido específico mediante su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Detalles del pedido obtenidos exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDto> getOrderById(
            @Parameter(description = "ID del pedido a obtener", required = true) @PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }
}
