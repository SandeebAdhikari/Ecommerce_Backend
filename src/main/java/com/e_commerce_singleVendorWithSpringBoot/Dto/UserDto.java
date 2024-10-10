package com.e_commerce_singleVendorWithSpringBoot.Dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private long userId;

    private String userName;
    private String email;

    private String password;
    private String role;

    private String token;

}
