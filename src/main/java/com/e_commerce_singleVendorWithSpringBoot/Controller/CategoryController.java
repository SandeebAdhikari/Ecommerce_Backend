package com.e_commerce_singleVendorWithSpringBoot.Controller;

import com.e_commerce_singleVendorWithSpringBoot.Dto.CategoryDto;
import com.e_commerce_singleVendorWithSpringBoot.ServiceImpl.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/admin/categories")
public class CategoryController {

    @Autowired
    private CategoryServiceImpl categoryService;

    // Endpoint to create a new category
    @PostMapping("/create")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody @Valid CategoryDto categoryDto) {
        // Call the service method to create a new category
        CategoryDto createdCategory = categoryService.createCategory(categoryDto);
        // Return the created category as the response
        return ResponseEntity.ok(createdCategory);
    }
    @GetMapping("/view/{categoryId}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable long categoryId) {
        CategoryDto categoryDto = categoryService.getCategoryById(categoryId);
        return new ResponseEntity<>(categoryDto, HttpStatus.OK);
    }
    @PutMapping("/update/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable long categoryId, @RequestBody CategoryDto categoryDto) {
        CategoryDto updatedCategory = categoryService.updateCategory(categoryId, categoryDto);
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }
    // Endpoint to delete a category by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCategoryById(@PathVariable long id) {
        boolean isDeleted = categoryService.deleteCategoryById(id);
        if (isDeleted) {
            return ResponseEntity.ok("Category deleted successfully.");
        } else {
            return ResponseEntity.status(404).body("Category not found.");
        }
    }
    @GetMapping("view/all")
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        List<CategoryDto> allCategories = categoryService.getAllCategories();
        if (allCategories != null) {
            return ResponseEntity.ok(allCategories);

        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
