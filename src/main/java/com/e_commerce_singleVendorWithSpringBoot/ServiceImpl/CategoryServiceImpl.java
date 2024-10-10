package com.e_commerce_singleVendorWithSpringBoot.ServiceImpl;

import com.e_commerce_singleVendorWithSpringBoot.Dto.CategoryDto;
import com.e_commerce_singleVendorWithSpringBoot.Entity.Category;
import com.e_commerce_singleVendorWithSpringBoot.Repository.CategoryRepository;
import com.e_commerce_singleVendorWithSpringBoot.Services.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepo;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        // Create a new Category entity
        Category category = new Category();
        // Copy properties from the DTO to the entity
        BeanUtils.copyProperties(categoryDto, category);
        // Save the new category to the repository
        Category savedCategory = categoryRepo.save(category);
        // Create a response DTO
        CategoryDto responseDto = new CategoryDto();
        // Copy the saved category entity back to the response DTO
        BeanUtils.copyProperties(savedCategory, responseDto);
        // Return the DTO
        return responseDto;
    }
    @Override
    public boolean deleteCategoryById(long categoryId) {
        // Check if the category exists
        Optional<Category> categoryOptional = categoryRepo.findById(categoryId);
        if (categoryOptional.isPresent()) {
            // Delete the category if it exists
            categoryRepo.deleteById(categoryId);
            System.out.println("Category deleted successfully with ID : " + categoryId);
            return true; // Return true after successful deletion
        } else {
            System.out.println("Category not found with ID : " + categoryId);
            return false; // Return false if the category was not found
        }
    }

    @Override
    public CategoryDto updateCategory(long categoryId, CategoryDto categoryDto) {
        // Fetch the existing category by ID
        Optional<Category> categoryOptional = categoryRepo.findById(categoryId);
        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            category.setCategoryName(categoryDto.getCategoryName());
            // Save the updated category
            Category updatedCategory = categoryRepo.save(category);
            // Prepare the response DTO
            CategoryDto responseDto = new CategoryDto();
            BeanUtils.copyProperties(updatedCategory, responseDto);
            return responseDto;
        } else {
            throw new RuntimeException("Category not found with ID: " + categoryId);
        }
    }

    @Override
    public CategoryDto getCategoryById(long categoryId) {
        Optional<Category> categoryOptional = categoryRepo.findById(categoryId);
        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            CategoryDto responseDto = new CategoryDto();
            BeanUtils.copyProperties(category, responseDto);
            return responseDto;
        } else {
            throw new RuntimeException("Category not found with ID: " + categoryId);
        }
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryRepo.findAll();
        if (categories.isEmpty()) {
            return Collections.emptyList();
        }
        return categories.stream().map(category -> {
            CategoryDto dto = new CategoryDto();
            BeanUtils.copyProperties(category, dto);
            return dto;
        }).collect(Collectors.toList());
    }

}
