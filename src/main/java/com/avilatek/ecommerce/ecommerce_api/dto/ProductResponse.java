package com.avilatek.ecommerce.ecommerce_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse<T> {
    private List<T> items;
    private int currentPage;
    private int totalPages;
    private long totalItems;


    // Getters y setters

    @Override
    public String toString() {
        return "ProductResponse{" +
                "items=" + items +
                ", currentPage=" + currentPage +
                ", totalPages=" + totalPages +
                ", totalItems=" + totalItems +
                '}';
    }
}
