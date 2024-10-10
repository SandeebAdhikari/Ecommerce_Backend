package com.e_commerce_singleVendorWithSpringBoot.Services;

import com.e_commerce_singleVendorWithSpringBoot.Dto.CartDto;

public interface CartService {
    CartDto addToCart(long userId, long productId, int quantity);

    CartDto addToCart(long userId, int productId, int quantity);

    CartDto removeFromCart(long userId, long productId);
    CartDto getCartByUserId(long userId);
    void clearCart(long userId);
}
