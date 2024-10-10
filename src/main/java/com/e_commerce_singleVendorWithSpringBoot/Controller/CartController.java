package com.e_commerce_singleVendorWithSpringBoot.Controller;

import com.e_commerce_singleVendorWithSpringBoot.Dto.CartDto;
import com.e_commerce_singleVendorWithSpringBoot.Service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<CartDto> addToCart(@RequestParam long userId, @RequestParam long productId, @RequestParam int quantity) {
        CartDto cartDto = cartService.addToCart(userId, productId, quantity);
        return ResponseEntity.ok(cartDto);
    }

    @PostMapping("/remove")
    public ResponseEntity<CartDto> removeFromCart(@RequestParam long userId, @RequestParam long productId) {
        CartDto cartDto = cartService.removeFromCart(userId, productId);
        return ResponseEntity.ok(cartDto);
    }

    @GetMapping("/view")
    public ResponseEntity<CartDto> getCart(@RequestParam long userId) {
        CartDto cartDto = cartService.getCartByUserId(userId);
        return ResponseEntity.ok(cartDto);
    }

    @DeleteMapping("/clear")
    public ResponseEntity<Void> clearCart(@RequestParam long userId) {
        cartService.clearCart(userId);
        return ResponseEntity.noContent().build();
    }
}
