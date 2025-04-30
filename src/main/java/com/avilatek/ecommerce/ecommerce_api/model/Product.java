package com.avilatek.ecommerce.ecommerce_api.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
@Table(
    name = "product",
    indexes = {
        @Index(name = "idx_product_name", columnList = "name"),
        @Index(name = "idx_stock_product", columnList = "stockQuantity"),
        @Index(name = "idx_price_product_name", columnList = "price, name"),
        @Index(name = "idx_price", columnList = "price")
    }
)

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq")
    @SequenceGenerator(name = "product_seq", sequenceName = "product_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer stockQuantity;
}