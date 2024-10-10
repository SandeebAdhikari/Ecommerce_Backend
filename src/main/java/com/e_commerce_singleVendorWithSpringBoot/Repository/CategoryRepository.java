package com.e_commerce_singleVendorWithSpringBoot.Repository;

import com.e_commerce_singleVendorWithSpringBoot.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
