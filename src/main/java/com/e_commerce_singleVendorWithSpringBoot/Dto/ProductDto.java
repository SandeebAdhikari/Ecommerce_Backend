package com.e_commerce_singleVendorWithSpringBoot.Dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private long productId;

    @NotBlank(message = "Product name is required")
    private String productName;

    @NotNull(message = "Price is required")
    @Min(value = 0, message = "Price must be equal or greater than 0")
    private Double price;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

    private String description;

    @NotNull(message = "Category ID is required")
    @Min(value = 1, message = "Category ID must be at least 1")
    private Long categoryId;

    private Double discount;
    @NotNull(message = "Price is required")
    @Min(value = 0, message = "Price must be equal or greater than 0")
    private Double finalPrice;
    private String categoryName;


}
