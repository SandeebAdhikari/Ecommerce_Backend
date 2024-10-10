package com.e_commerce_singleVendorWithSpringBoot.Services;

import com.e_commerce_singleVendorWithSpringBoot.Dto.CategoryDto;

import java.util.List;

public interface CategoryService {

    CategoryDto createCategory(CategoryDto categoryDto);
    boolean deleteCategoryById(long categoryId);

    CategoryDto updateCategory(long categoryId, CategoryDto categoryDto);
    CategoryDto getCategoryById(long categoryId);
    List<CategoryDto> getAllCategories();
}
