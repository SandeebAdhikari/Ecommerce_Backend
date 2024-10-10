package com.e_commerce_singleVendorWithSpringBoot.Controller;

import com.e_commerce_singleVendorWithSpringBoot.Dto.UserDto;
import com.e_commerce_singleVendorWithSpringBoot.ExceptionHandler.GlobalExceptionHandler;
import com.e_commerce_singleVendorWithSpringBoot.ServiceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserServiceImpl serviceImpl;  // Inject the service layer

    // Get role of a user
    @GetMapping("/role")
    public ResponseEntity<UserDto> getRole(@RequestBody UserDto userDto) {
        UserDto userWithRole = serviceImpl.getRole(userDto);
        if (userWithRole != null) {
            return ResponseEntity.ok(userWithRole);
        } else {
            return ResponseEntity.status(404).body(null);  // Return 404 if user is not found
        }
    }

    // Promote User to Admin endpoint
    @PutMapping("/promote/{email}")
    public ResponseEntity<?> promoteUserToAdmin(@PathVariable String email) {
        try {
            UserDto updatedUser = serviceImpl.promoteUserToAdmin(email);  // Call the service method
            return ResponseEntity.ok(updatedUser);  // Return the updated user with the new role
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new GlobalExceptionHandler.ErrorResponse(e.getMessage()));
        }
    }

    // Update User endpoint (Admin)
    @PutMapping("/update")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto) {
        UserDto updatedUser = serviceImpl.updateUser(userDto);  // Directly call the service method
        if (updatedUser != null) {
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    // Delete User endpoint (Admin)
    @DeleteMapping("/deactivate/{email}")
    public ResponseEntity<String> deactivateUser(@PathVariable String email) {
        boolean isDeleted = serviceImpl.deactivateUser(email);  // Directly call the service method
        if (isDeleted) {
            return ResponseEntity.ok("User deleted successfully.");
        } else {
            return ResponseEntity.status(404).body("User not found.");
        }
    }

    // View all users (Admin)
    @GetMapping("/view/all")
    public ResponseEntity<List<UserDto>> viewAllUsers() {
        List<UserDto> allUsers = serviceImpl.viewAllUsers();  // Directly call the service method
        return ResponseEntity.ok(allUsers);
    }
}
