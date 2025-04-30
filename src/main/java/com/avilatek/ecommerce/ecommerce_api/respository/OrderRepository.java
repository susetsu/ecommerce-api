package com.avilatek.ecommerce.ecommerce_api.respository;



import com.avilatek.ecommerce.ecommerce_api.model.Order;
import com.avilatek.ecommerce.ecommerce_api.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findByUser(User user, Pageable pageable);
}