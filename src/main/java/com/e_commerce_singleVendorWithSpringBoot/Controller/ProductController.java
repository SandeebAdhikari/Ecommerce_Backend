package com.e_commerce_singleVendorWithSpringBoot.Controller;

import com.e_commerce_singleVendorWithSpringBoot.Dto.ProductDto;
import com.e_commerce_singleVendorWithSpringBoot.Repository.ProductRepository;
import com.e_commerce_singleVendorWithSpringBoot.ServiceImpl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/admin/product")
public class ProductController {

    @Autowired
    private ProductServiceImpl productService;


    // Endpoint to create a new product.
    @PostMapping("/create")
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        // Calls the createProduct method from ProductService to save the product.
        ProductDto createdProduct = productService.createProduct(productDto);
        // Returns the created product as the response
        return ResponseEntity.ok(createdProduct);

    }
    @PutMapping("/update/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable long id, @RequestBody ProductDto productDto) {
        ProductDto updatedProduct = productService.updateProduct(id, productDto);
        if (updatedProduct != null) {
            return ResponseEntity.ok(updatedProduct);
        }
        else {
            return ResponseEntity.notFound().build();
        }

    }
    @GetMapping("/view/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable int id) {
        // Calls the getProductById method from ProductService to fetch the product details.
        ProductDto productDto = productService.getProductById(id);
        if (productDto != null) {
            return ResponseEntity.ok(productDto);
        }
        else {
            return ResponseEntity.notFound().build();
        }

    }
    @GetMapping("/view/all")
    public ResponseEntity<List<ProductDto>> viewAllProducts() {
        // Calls the viewAllProducts method from ProductService to fetch all products.
        List<ProductDto> allProducts = productService.viewAllProducts();
        if (allProducts != null) {
            return ResponseEntity.ok(allProducts);

        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProductById(@PathVariable int id) {
        boolean isDeleted = productService.deleteProductById(id);
        if (isDeleted) {
            return ResponseEntity.ok("Product deleted successfully.");
        } else {
            return ResponseEntity.status(404).body("Product not found.");
        }
    }

}