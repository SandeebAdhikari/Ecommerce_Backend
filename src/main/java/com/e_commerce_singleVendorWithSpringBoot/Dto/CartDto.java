package com.e_commerce_singleVendorWithSpringBoot.Dto;

import com.e_commerce_singleVendorWithSpringBoot.Entity.Cart;
import com.e_commerce_singleVendorWithSpringBoot.Entity.CartItem;
import lombok.Data;

import java.util.List;

@Data
public class CartDto {

    private long cartId;
    private List<CartItem> cartItems;
    private double totalPrice;

    public CartDto(Cart cart) {
        this.cartId = cart.getCartId();
        this.cartItems = cart.getCartItems();
        this.totalPrice = cart.getTotalPrice();
    }
}
