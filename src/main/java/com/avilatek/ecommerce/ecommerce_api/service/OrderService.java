package com.avilatek.ecommerce.ecommerce_api.service;


import com.avilatek.ecommerce.ecommerce_api.dto.OrderDto;
import com.avilatek.ecommerce.ecommerce_api.dto.OrderItemResponseDto;
import com.avilatek.ecommerce.ecommerce_api.dto.OrderResponseDto;
import com.avilatek.ecommerce.ecommerce_api.dto.PaginatedOrderResponse;
import com.avilatek.ecommerce.ecommerce_api.model.Order;
import com.avilatek.ecommerce.ecommerce_api.model.OrderItem;
import com.avilatek.ecommerce.ecommerce_api.model.Product;
import com.avilatek.ecommerce.ecommerce_api.model.User;
import com.avilatek.ecommerce.ecommerce_api.model.enums.OrderStatus;
import com.avilatek.ecommerce.ecommerce_api.respository.OrderItemRepository;
import com.avilatek.ecommerce.ecommerce_api.respository.OrderRepository;
import com.avilatek.ecommerce.ecommerce_api.respository.ProductRepository;
import com.avilatek.ecommerce.ecommerce_api.respository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository,
                        ProductRepository productRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Order createOrder(OrderDto orderDto) {
        // Obtener el usuario autenticado
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Crear el pedido
        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);
        order.setTotalAmount(orderDto.getTotalAmount());

        Order savedOrder = orderRepository.save(order);

        // Crear los items del pedido
        List<OrderItem> orderItems = orderDto.getItems().stream()
                .map(itemDto -> {
                    Product product = productRepository.findById(itemDto.getProductId())
                            .orElseThrow(() -> new RuntimeException("Product not found: " + itemDto.getProductId()));

                    // Verificar stock
                    if (product.getStockQuantity() < itemDto.getQuantity()) {
                        throw new RuntimeException("Insufficient stock for product: " + product.getName());
                    }

                    // Actualizar stock
                    product.setStockQuantity(product.getStockQuantity() - itemDto.getQuantity());
                    productRepository.save(product);

                    OrderItem orderItem = new OrderItem();
                    orderItem.setOrder(savedOrder);
                    orderItem.setProduct(product);
                    orderItem.setQuantity(itemDto.getQuantity());
                    orderItem.setPrice(itemDto.getPrice());

                    return orderItem;
                })
                .collect(Collectors.toList());

        orderItemRepository.saveAll(orderItems);
        savedOrder.setOrderItems(orderItems);

        return savedOrder;
    }

    @Transactional(readOnly = true)
    public PaginatedOrderResponse getUserOrders(Pageable pageable) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Page<Order> ordersPage = orderRepository.findByUser(user, pageable);
        Page<OrderResponseDto> dtoPage = ordersPage.map(this::convertToOrderResponseDto);

        return new PaginatedOrderResponse(dtoPage);
    }

    @Transactional(readOnly = true)
    public OrderResponseDto getOrderById(Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Order order = orderRepository.findById(id)
                .filter(o -> o.getUser().getUsername().equals(username))
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado o no perteneciente al usuario"));

        return convertToOrderResponseDto(order);
    }

    private OrderResponseDto convertToOrderResponseDto(Order order) {
        OrderResponseDto dto = new OrderResponseDto();
        dto.setId(order.getId());
        dto.setOrderDate(order.getOrderDate());
        dto.setStatus(order.getStatus().name());
        dto.setTotalAmount(order.getTotalAmount());

        List<OrderItemResponseDto> itemDtos = order.getOrderItems().stream()
                .map(this::convertToOrderItemResponseDto)
                .collect(Collectors.toList());

        dto.setItems(itemDtos);
        return dto;
    }

    private OrderItemResponseDto convertToOrderItemResponseDto(OrderItem item) {
        OrderItemResponseDto dto = new OrderItemResponseDto();
        dto.setProductId(item.getProduct().getId());
        dto.setProductName(item.getProduct().getName()); // Asume que Product tiene getName()
        dto.setQuantity(item.getQuantity());
        dto.setPrice(item.getPrice());
        dto.setSubtotal(item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        return dto;
    }
}