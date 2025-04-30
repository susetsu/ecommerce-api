package com.avilatek.ecommerce.ecommerce_api.dto;


import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderDto {
    private List<OrderItemDto> items;
    private BigDecimal totalAmount;
}