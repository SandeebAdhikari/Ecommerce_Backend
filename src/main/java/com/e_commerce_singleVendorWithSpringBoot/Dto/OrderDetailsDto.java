package com.e_commerce_singleVendorWithSpringBoot.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailsDto {
        private Long orderDetailsId;
        private Long orderId;
        private int productId;
        private String productName;
        private int quantity;
        private Double price;
        private Double subtotal;

}
