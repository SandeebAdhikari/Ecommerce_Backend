package com.e_commerce_singleVendorWithSpringBoot.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long productId;
    private String productName;
    private String description;
    private double price;
    private int quantity;
    private String status;
    private Double discount;
    private Double finalPrice;

    // Many products can belong to one category
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;

//    // Many products can belong to one user (who created it)
//    @ManyToOne
//    @JoinColumn(name = "user_id", nullable = true)  // Make it nullable temporarily
//    private User createdBy;  // Ensure this matches the "user_id" in the User entity

}
