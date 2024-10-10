package com.e_commerce_singleVendorWithSpringBoot.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long cartItemId;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;  // Link to the product entity

    private int quantity;  // Quantity of the product in the cart
    private double price;  // Price of this product (quantity * price per unit)
}
