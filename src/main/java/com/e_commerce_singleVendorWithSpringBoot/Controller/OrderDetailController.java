package com.e_commerce_singleVendorWithSpringBoot.Controller;

import com.e_commerce_singleVendorWithSpringBoot.Dto.OrderDetailsDto;
import com.e_commerce_singleVendorWithSpringBoot.Services.OrderDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order-details")
public class OrderDetailController {

    @Autowired
    private OrderDetailsService orderDetailsService;

    @PostMapping("/add")
    public ResponseEntity<OrderDetailsDto> addOrderDetails(@RequestBody OrderDetailsDto orderDetailsDto) {
        OrderDetailsDto createdDetails = orderDetailsService.createOrderDetails(orderDetailsDto);
        return ResponseEntity.ok(createdDetails);
    }

    // Get all details for a specific order
    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<OrderDetailsDto>> getOrderDetailsByOrderId(@PathVariable Long orderId) {
        List<OrderDetailsDto> orderDetails = orderDetailsService.getOrderDetailsByOrderId(orderId);
        return ResponseEntity.ok(orderDetails);
    }

    // Delete specific order details
    @DeleteMapping("/{orderDetailsId}")
    public ResponseEntity<Void> deleteOrderDetails(@PathVariable Long orderDetailsId) {
        orderDetailsService.deleteOrderDetails(orderDetailsId);
        return ResponseEntity.noContent().build();
    }
}
