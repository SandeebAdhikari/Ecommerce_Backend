package com.e_commerce_singleVendorWithSpringBoot.Controller;

import com.e_commerce_singleVendorWithSpringBoot.Dto.UserDto;
import com.e_commerce_singleVendorWithSpringBoot.Repository.UserRepository;
import com.e_commerce_singleVendorWithSpringBoot.ServiceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    //inject
    @Autowired
    private UserServiceImpl serviceImpl;
    @Autowired
    private UserRepository UserRepo;


    // User registration endpoint
    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto) {
        UserDto registeredUser = serviceImpl.registerUser(userDto);
        return ResponseEntity.ok(registeredUser);
    }

    // User login endpoint
    @PostMapping("/login")
    public ResponseEntity<UserDto> loginUser(@RequestBody UserDto userDto) {
        UserDto loggedInUser = serviceImpl.loginUser(userDto);
        if (loggedInUser != null) {
            return ResponseEntity.ok(loggedInUser);
        } else {
            return ResponseEntity.status(401).body(null);  // Return 401 Unauthorized if login fails
        }
    }
    // Update User endpoint
    @PutMapping("/update")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto) {
        UserDto updatedUser = serviceImpl.updateUser(userDto);
        if (updatedUser != null) {
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    // Delete User endpoint
    @DeleteMapping("/deactivate/{email}")
    public ResponseEntity<String> deactivateUser(@PathVariable String email) {
        boolean isDeleted = serviceImpl.deactivateUser(email);
        if (isDeleted) {
            return ResponseEntity.ok("User deleted successfully.");
        } else {
            return ResponseEntity.status(404).body("User not found.");
        }
    }
    // View all users endpoint
    @GetMapping("view/all")
    public ResponseEntity<List<UserDto>> viewAllUsers() {
        List<UserDto> allUsers = serviceImpl.viewAllUsers();
        return ResponseEntity.ok(allUsers);
    }
}
