package com.e_commerce_singleVendorWithSpringBoot.Entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Admin extends User {

    private long userId;
    private String department;
    private boolean hasSuperAdminAccess;


    public Admin(long userId, String userName, String email, String password, Role role, String department, boolean hasSuperAdminAccess) {
        super(userName, email, password, role);
        this.userId = userId;
        this.department = department;
        this.hasSuperAdminAccess = hasSuperAdminAccess;
    }
}
