package com.avilatek.ecommerce.ecommerce_api.respository;


import com.avilatek.ecommerce.ecommerce_api.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}