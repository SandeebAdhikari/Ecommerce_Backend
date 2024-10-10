package com.e_commerce_singleVendorWithSpringBoot.ServiceImpl;

import com.e_commerce_singleVendorWithSpringBoot.Dto.OrderDetailsDto;
import com.e_commerce_singleVendorWithSpringBoot.Entity.Order;
import com.e_commerce_singleVendorWithSpringBoot.Entity.OrderDetails;
import com.e_commerce_singleVendorWithSpringBoot.Entity.Product;
import com.e_commerce_singleVendorWithSpringBoot.Repository.OrderDetailsRepository;
import com.e_commerce_singleVendorWithSpringBoot.Repository.OrderRepository;
import com.e_commerce_singleVendorWithSpringBoot.Repository.ProductRepository;
import com.e_commerce_singleVendorWithSpringBoot.Services.OrderDetailsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class OrderDetailsServiceImpl implements OrderDetailsService {

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public OrderDetailsDto createOrderDetails(OrderDetailsDto orderDetailsDto) {
        OrderDetails orderDetails = new OrderDetails();

        // Fetch Order entity from repository (ensure orderId is Long)
        Order order = orderRepository.findById(orderDetailsDto.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // Fetch Product entity from repository (ensure productId is Long)
        Product product = productRepository.findById(orderDetailsDto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Set values in the OrderDetails entity
        orderDetails.setOrder(order);
        orderDetails.setProduct(product);
        orderDetails.setQuantity(orderDetailsDto.getQuantity());
        orderDetails.setPrice(product.getPrice());
        orderDetails.setSubtotal(product.getPrice() * orderDetailsDto.getQuantity());

        // Save the order details entity in the database
        OrderDetails savedOrderDetails = orderDetailsRepository.save(orderDetails);

        // Copy saved entity to the response DTO
        OrderDetailsDto responseDto = new OrderDetailsDto();
        BeanUtils.copyProperties(savedOrderDetails, responseDto);
        responseDto.setProductName(product.getProductName());
        responseDto.setOrderId(order.getOrderId());

        return responseDto;
    }

    @Override
    public List<OrderDetailsDto> getOrderDetailsByOrderId(Long orderId) {
        // Fetch order details by Order ID
        List<OrderDetails> orderDetailsList = orderDetailsRepository.findByOrder_OrderId(orderId);
        // Map entity list to DTO list and return
        return orderDetailsList.stream().map(orderDetails -> {
            OrderDetailsDto dto = new OrderDetailsDto();
            BeanUtils.copyProperties(orderDetails, dto);
            dto.setProductName(orderDetails.getProduct().getProductName());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public void deleteOrderDetails(Long orderDetailsId) {
        // Delete the order details by ID
        orderDetailsRepository.deleteById(orderDetailsId);
    }
}
