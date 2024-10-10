package com.e_commerce_singleVendorWithSpringBoot.Services;


import com.e_commerce_singleVendorWithSpringBoot.Dto.UserDto;

public interface userService {
    UserDto registerUser(UserDto userDto);
    UserDto loginUser(UserDto userDto);
    UserDto updateUser(UserDto userDto);
    UserDto getRole(UserDto userDto);
}
