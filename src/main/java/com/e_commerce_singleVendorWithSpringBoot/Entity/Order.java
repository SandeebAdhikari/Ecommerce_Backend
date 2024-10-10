package com.e_commerce_singleVendorWithSpringBoot.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Orders")
public class Order {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long orderId;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id", nullable = false)
        private User user;

        @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
        private List<OrderDetails> orderDetailsList;

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



