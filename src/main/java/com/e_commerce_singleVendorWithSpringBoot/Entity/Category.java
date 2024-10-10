package com.e_commerce_singleVendorWithSpringBoot.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long categoryId;
    private String categoryName;
    private String description;

    // One category can have many products
    @OneToMany(mappedBy = "category", orphanRemoval = true)
    private List<Product> products;
}
