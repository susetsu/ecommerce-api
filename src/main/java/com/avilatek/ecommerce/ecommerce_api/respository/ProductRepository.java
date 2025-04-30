package com.avilatek.ecommerce.ecommerce_api.respository;


import com.avilatek.ecommerce.ecommerce_api.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findAll(Pageable pageable);
    Page<Product> findByStockQuantityGreaterThan(int quantity, Pageable pageable);
}