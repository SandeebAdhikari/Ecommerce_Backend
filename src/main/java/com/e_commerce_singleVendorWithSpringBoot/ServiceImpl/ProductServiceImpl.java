package com.e_commerce_singleVendorWithSpringBoot.ServiceImpl;

import com.e_commerce_singleVendorWithSpringBoot.Dto.CategoryDto;
import com.e_commerce_singleVendorWithSpringBoot.Dto.ProductDto;
import com.e_commerce_singleVendorWithSpringBoot.Entity.Category;
import com.e_commerce_singleVendorWithSpringBoot.Entity.Product;
import com.e_commerce_singleVendorWithSpringBoot.Repository.ProductRepository;
import com.e_commerce_singleVendorWithSpringBoot.Repository.CategoryRepository;
import com.e_commerce_singleVendorWithSpringBoot.Services.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepo;

    // Inject Category repository to fetch Category by ID
    @Autowired
   private CategoryRepository categoryRepo;

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        //Create a new Product entity instance
        Product product = new Product();
        //Copy properties from the input ProductDto (DTO) to the Product entity
        BeanUtils.copyProperties(productDto, product);
        // Set the status based on the quantity
        if (productDto.getQuantity() > 0) {
            product.setStatus("Available");
        } else {
            product.setStatus("Not Available");
        }

       // Fetch Category entity using categoryId from productDto, or create a default category if none exists
        Optional<Category> categoryOptional = categoryRepo.findById(productDto.getCategoryId());
        // If category does not exist, throw an exception
        if (!categoryOptional.isPresent()) {
            throw new RuntimeException("Category not found for ID: " + productDto.getCategoryId());
        } else {
            // If the category exists, assign it to the product
            product.setCategory(categoryOptional.get());
        }

        // Calculate finalPrice based on price * quantity
        double calculatedFinalPrice = productDto.getPrice() * productDto.getQuantity() - productDto.getDiscount();
        product.setFinalPrice(calculatedFinalPrice);

        // Check for null values for discount and finalPrice
        if (productDto.getDiscount() == null) {
            product.setDiscount(0.0);  // Set a default value for discount
        }
        if (productDto.getFinalPrice() == null) {
            product.setFinalPrice(productDto.getPrice());
        }
        // Save the new Product entity to the database via the ProductRepo
       Product saveProduct = productRepo.save(product);
       // Create a new ProductDto instance to hold the response data
       ProductDto responseDto = new ProductDto();
       //Copy properties from the saved Product entity to the response ProductDto
       BeanUtils.copyProperties(saveProduct, responseDto);
        // Set the category details in the responseDto
        Category category = saveProduct.getCategory();
        responseDto.setCategoryId(category.getCategoryId());
        responseDto.setCategoryName(category.getCategoryName());
        // Set the correct finalPrice in the responseDto
        responseDto.setFinalPrice(calculatedFinalPrice);
        return responseDto;
    }

    @Override
    public ProductDto updateProduct(long productId, ProductDto productDto) {
        int id = (int) productId;
        //fetch the product from repo
        Optional<Product> optionalProduct = productRepo.findById(id);
        if(optionalProduct.isPresent()){
            //retrieve the product entity
            Product product = optionalProduct.get();
            //update product entity with the new values from provided  ProductDto
            product.setProductName(productDto.getProductName());
            product.setPrice(productDto.getPrice());
            product.setQuantity(productDto.getQuantity());
            product.setDescription(productDto.getDescription());
            // Set the status based on the updated quantity
            if (productDto.getQuantity() > 0) {
                product.setStatus("Available");
            } else {
                product.setStatus("Not Available");
            }
            // Calculate finalPrice based on price * quantity
            double calculatedFinalPrice = productDto.getPrice() * productDto.getQuantity() - productDto.getDiscount();
            product.setFinalPrice(calculatedFinalPrice);

            // Fetch Category entity using categoryId from productDto
            Optional<Category> categoryOptional = categoryRepo.findById(productDto.getCategoryId());
            if (categoryOptional.isPresent()) {
                product.setCategory(categoryOptional.get()); // Update the product category
            } else {
                throw new RuntimeException("Category not found for ID: " + productDto.getCategoryId());
            }
            //save the updated entity back to the repo
            Product updatedProduct = productRepo.save(product);
            //create a new ProductDto to send back as a response
            ProductDto responseDto = new ProductDto();
            //copy the  updated product properties into responseDto
            BeanUtils.copyProperties(updatedProduct, responseDto);
            // Set the categoryId and categoryName manually in the responseDto
            responseDto.setCategoryId(updatedProduct.getCategory().getCategoryId());
            responseDto.setCategoryName(updatedProduct.getCategory().getCategoryName());
            // Set the correct finalPrice in the responseDto
            responseDto.setFinalPrice(calculatedFinalPrice);

            return responseDto;
        }
        return null;
    }

    @Override
    public ProductDto getProductById(int id) {
        //fetch the product from repo
        Optional<Product> optionalProduct = productRepo.findById(id);
        if(optionalProduct.isPresent()){
            Product product = optionalProduct.get();
            ProductDto responseDto = new ProductDto();
            // Include category details in the response
            BeanUtils.copyProperties(product, responseDto);

            // Fetch category details from the product entity and set them in responseDto
            if (product.getCategory() != null) {
                responseDto.setCategoryId(product.getCategory().getCategoryId());  // Set categoryId
                responseDto.setCategoryName(product.getCategory().getCategoryName());  // Set categoryName
            }
            return responseDto;

        }
        return null;
    }

    @Override
    public List<ProductDto> viewAllProducts() {
        // Fetch all products from the repo
        List<Product> products = productRepo.findAll();
        // Create a list to hold ProductDto objects
        List<ProductDto> productDtoList = new ArrayList<>();
        // Loop through each product and convert it to ProductDto
        for (Product product : products) {
            ProductDto productDto = new ProductDto();
            BeanUtils.copyProperties(product, productDto);
            // If the product has a category, set the category details
            if (product.getCategory() != null) {
                productDto.setCategoryId(product.getCategory().getCategoryId());
                productDto.setCategoryName(product.getCategory().getCategoryName());
            }

            //add to the list
            productDtoList.add(productDto);
        }
        //return the list of productDto
        return productDtoList;
    }

    @Override
    public boolean deleteProductById(int id) {
        Optional<Product> optionalProduct = productRepo.findById(id);
        if(optionalProduct.isPresent()) {
            productRepo.deleteById(id);
            System.out.println("Product deleted successfully with ID : " + id);
            return true;  // Return true after a successful deletion
        } else {
            System.out.println("Product not found with ID : " + id);
            return false;  // Return false if the product was not found
        }
    }

}
