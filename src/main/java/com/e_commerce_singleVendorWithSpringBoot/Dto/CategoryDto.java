package com.e_commerce_singleVendorWithSpringBoot.Dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {

    private long categoryId;

    @NotBlank(message = "Category name is required")
    private String categoryName;

    private String description;
}
