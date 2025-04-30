package com.avilatek.ecommerce.ecommerce_api.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class OrderItemResponseDto {
    private Long productId;
    private String productName; // Agrega más detalles según necesites
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal subtotal;
}