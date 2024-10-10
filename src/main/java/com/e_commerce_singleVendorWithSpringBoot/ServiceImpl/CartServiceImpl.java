package com.e_commerce_singleVendorWithSpringBoot.ServiceImpl;

import com.e_commerce_singleVendorWithSpringBoot.Dto.CartDto;
import com.e_commerce_singleVendorWithSpringBoot.Entity.Cart;
import com.e_commerce_singleVendorWithSpringBoot.Entity.CartItem;
import com.e_commerce_singleVendorWithSpringBoot.Entity.Product;
import com.e_commerce_singleVendorWithSpringBoot.Repository.CartRepository;
import com.e_commerce_singleVendorWithSpringBoot.Repository.ProductRepository;
import com.e_commerce_singleVendorWithSpringBoot.Repository.UserRepository;
import com.e_commerce_singleVendorWithSpringBoot.Service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public CartDto addToCart(long userId, long productId, int quantity) {
        return null;
    }

    @Override
    public CartDto addToCart(long userId, int productId, int quantity) {
        // Fetch the product
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("Product not found"));

        // Fetch the user's cart or create a new one
        Cart cart = cartRepository.findByUser_UserId(userId).orElse(new Cart());

        // Add the item to the cart
        List<CartItem> cartItems = cart.getCartItems();
        Optional<CartItem> existingItem = cartItems.stream()
                .filter(item -> item.getProduct().getProductId() == productId)
                .findFirst();

        if (existingItem.isPresent()) {
            // Update quantity if the item is already in the cart
            CartItem cartItem = existingItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        } else {
            // Add new item to the cart
            CartItem cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setPrice(product.getPrice() * quantity);
            cartItems.add(cartItem);
        }

        cartRepository.save(cart);

        return new CartDto(cart);
    }


    @Override
    public CartDto removeFromCart(long userId, long productId) {
        Cart cart = cartRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new NoSuchElementException("Cart not found"));

        cart.getCartItems().removeIf(item -> item.getProduct().getProductId() == productId);

        cartRepository.save(cart);
        return new CartDto(cart);
    }

    @Override
    public CartDto getCartByUserId(long userId) {
        Cart cart = cartRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new NoSuchElementException("Cart not found"));

        return new CartDto(cart);
    }

    @Override
    public void clearCart(long userId) {
        Cart cart = cartRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new NoSuchElementException("Cart not found"));

        cart.getCartItems().clear();
        cartRepository.save(cart);
    }
}
