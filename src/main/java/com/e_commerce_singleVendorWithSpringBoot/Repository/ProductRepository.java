package com.e_commerce_singleVendorWithSpringBoot.Repository;

import com.e_commerce_singleVendorWithSpringBoot.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

}
