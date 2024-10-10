package com.e_commerce_singleVendorWithSpringBoot.ServiceImpl;

import com.e_commerce_singleVendorWithSpringBoot.Dto.OrderDto;
import com.e_commerce_singleVendorWithSpringBoot.Entity.Order;
import com.e_commerce_singleVendorWithSpringBoot.Entity.User;
import com.e_commerce_singleVendorWithSpringBoot.Repository.OrderRepository;
import com.e_commerce_singleVendorWithSpringBoot.Repository.UserRepository;
import com.e_commerce_singleVendorWithSpringBoot.Services.OrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

        @Autowired
        private OrderRepository orderRepository;

        @Autowired
        private UserRepository userRepository;

        @Override
        public OrderDto createOrder(OrderDto orderDto) {
            Order order = new Order();

            User user = userRepository.findById(orderDto.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found with ID: " + orderDto.getUserId()));


            BeanUtils.copyProperties(orderDto, order);
            order.setUser(user);

            Order savedOrder = orderRepository.save(order);
            OrderDto responseDto = new OrderDto();
            BeanUtils.copyProperties(savedOrder, responseDto);
            responseDto.setUserId(savedOrder.getUser().getUserId());

            return responseDto;
        }

        @Override
        public OrderDto getOrderById(Long orderId) {
            Order order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));


            OrderDto orderDto = new OrderDto();
            BeanUtils.copyProperties(order, orderDto);
            orderDto.setUserId(order.getUser().getUserId());

            return orderDto;
        }

        @Override
        public List<OrderDto> getOrdersByUserId(Long userId) {
            List<Order> orders = orderRepository.findByUser_UserId(userId);


            return orders.stream().map(order -> {
                OrderDto orderDto = new OrderDto();
                BeanUtils.copyProperties(order, orderDto);
                orderDto.setUserId(order.getUser().getUserId());
                return orderDto;
            }).collect(Collectors.toList());
        }

        @Override
        public void cancelOrder(Long orderId) {

            Order order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));

            order.setStatus("Canceled");

            orderRepository.save(order);
        }
    }


