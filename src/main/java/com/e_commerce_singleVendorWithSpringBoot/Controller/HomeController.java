package com.e_commerce_singleVendorWithSpringBoot.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping("/")
    public String apiInfo(){
        return "Welcome to E-commerce singleVendor with Spring Boot Api Application!!! ";
    }


}