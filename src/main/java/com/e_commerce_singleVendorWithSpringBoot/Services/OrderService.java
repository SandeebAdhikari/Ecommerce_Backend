package com.e_commerce_singleVendorWithSpringBoot.Services;

import com.e_commerce_singleVendorWithSpringBoot.Dto.OrderDto;

import java.util.List;

public interface OrderService {
    OrderDto createOrder(OrderDto orderDto);
    OrderDto getOrderById(Long orderId);  // Fix return type to match OrderController
    List<OrderDto> getOrdersByUserId(Long userId);  // Align the method name with the controller
    void cancelOrder(Long orderId);  // Method to cancel/delete an order
}
