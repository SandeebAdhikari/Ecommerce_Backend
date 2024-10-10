package com.e_commerce_singleVendorWithSpringBoot.Repository;

import com.e_commerce_singleVendorWithSpringBoot.Entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> findByEmail(String email);
}
