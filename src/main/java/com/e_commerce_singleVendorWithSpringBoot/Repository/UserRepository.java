package com.e_commerce_singleVendorWithSpringBoot.Repository;

import com.e_commerce_singleVendorWithSpringBoot.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
 Optional<User> findByEmail(String email);
}
