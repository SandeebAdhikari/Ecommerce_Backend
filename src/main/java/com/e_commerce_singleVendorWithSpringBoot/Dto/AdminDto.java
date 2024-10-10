package com.e_commerce_singleVendorWithSpringBoot.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminDto extends UserDto {

    private boolean adminPermissions;


    // Constructor to match the field type
    public AdminDto(long userId, String userName, String email, String password, String role, String token, boolean adminPermissions) {
        super(userId, userName, email, password, role, token);
        this.adminPermissions = adminPermissions;
    }


}
