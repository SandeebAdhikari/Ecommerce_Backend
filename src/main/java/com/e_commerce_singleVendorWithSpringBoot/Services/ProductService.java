package com.e_commerce_singleVendorWithSpringBoot.Services;

import com.e_commerce_singleVendorWithSpringBoot.Dto.ProductDto;

import java.util.List;

public interface ProductService {
    ProductDto createProduct(ProductDto productDto);
    ProductDto updateProduct(long productId,  ProductDto productDto);
    ProductDto getProductById(int id);
    List<ProductDto> viewAllProducts();
    boolean deleteProductById(int id);
}
