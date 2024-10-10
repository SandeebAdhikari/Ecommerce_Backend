package com.e_commerce_singleVendorWithSpringBoot.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long cartId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;  // Associate the cart with a user

    @OneToMany(cascade = CascadeType.ALL)
    private List<CartItem> cartItems;  // List of items in the cart

    private double totalPrice;  // Total price of the cart
}
