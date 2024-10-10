package com.e_commerce_singleVendorWithSpringBoot.Services;

import com.e_commerce_singleVendorWithSpringBoot.Dto.OrderDetailsDto;

import java.util.List;

public interface OrderDetailsService {
        OrderDetailsDto createOrderDetails(OrderDetailsDto orderDetailsDto);
        List<OrderDetailsDto> getOrderDetailsByOrderId(Long orderId);
        void deleteOrderDetails(Long orderDetailsId);
}
