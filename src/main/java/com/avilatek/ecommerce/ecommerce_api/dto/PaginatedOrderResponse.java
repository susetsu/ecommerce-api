package com.avilatek.ecommerce.ecommerce_api.dto;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class PaginatedOrderResponse {
    private List<OrderResponseDto> content;
    private int currentPage;
    private int totalPages;
    private long totalItems;

    public PaginatedOrderResponse(Page<OrderResponseDto> page) {
        this.content = page.getContent();
        this.currentPage = page.getNumber();
        this.totalPages = page.getTotalPages();
        this.totalItems = page.getTotalElements();
    }
}