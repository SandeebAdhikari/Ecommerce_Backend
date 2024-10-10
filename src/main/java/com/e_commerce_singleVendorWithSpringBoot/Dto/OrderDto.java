package com.e_commerce_singleVendorWithSpringBoot.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

        private Long orderId;
        private Long userId;
        private LocalDateTime orderDate;
        private Double totalPrice;
        private Double taxAmount;
        private String status;
        private String shippingAddress;
        private String paymentMethod;
        private String paymentStatus;
        private Double shippingCost;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

    }
