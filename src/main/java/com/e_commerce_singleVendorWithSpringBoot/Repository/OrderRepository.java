package com.e_commerce_singleVendorWithSpringBoot.Repository;

import com.e_commerce_singleVendorWithSpringBoot.Entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser_UserId(Long userId);
}
