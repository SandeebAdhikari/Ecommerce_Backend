package com.e_commerce_singleVendorWithSpringBoot.ServiceImpl;

import com.e_commerce_singleVendorWithSpringBoot.Entity.Role;
import com.e_commerce_singleVendorWithSpringBoot.Dto.UserDto;
import com.e_commerce_singleVendorWithSpringBoot.Entity.User;
import com.e_commerce_singleVendorWithSpringBoot.Repository.UserRepository;
import com.e_commerce_singleVendorWithSpringBoot.Services.userService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements userService {

    @Autowired
    private UserRepository UserRepo;
    @Autowired
    private JwtService jwtService;  // Inject JwtService
    @Autowired
    private PasswordEncoder passwordEncoder;  // Use this to encode passwords

    @Override
    public UserDto registerUser(UserDto usr) {
        User user = new User();
        BeanUtils.copyProperties(usr, user, "userId");

        long userCount = UserRepo.count();
        if (userCount == 0) {

            user.setRole(Role.ADMIN);
            System.out.println("First user, setting role to ADMIN.");
        } else {

            user.setRole(Role.USER);
            System.out.println("New user, setting role to USER.");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        System.out.println("Encoded Password : " + user.getPassword());

        User savedUser = UserRepo.save(user);

        UserDto Dto = new UserDto();
        BeanUtils.copyProperties(savedUser, Dto);

        Dto.setPassword(null);

        Dto.setRole(savedUser.getRole().name());
        return Dto;
    }

    @Override
    public UserDto loginUser(UserDto userDto) {
        Optional<User> userOptional = UserRepo.findByEmail(userDto.getEmail());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            System.out.println("User found: " + user.getUserName());
            // Log the encoded password in the database
            System.out.println("Encoded password in DB: " + user.getPassword());
            System.out.println("Raw password entered: " + userDto.getPassword());
            // Compare encrypted password using the passwordEncoder
            if (passwordEncoder.matches(userDto.getPassword(), user.getPassword())) {
                // Generate JWT token for the user
                String token = jwtService.generateToken(user.getEmail());
                // Prepare the response DTO
                UserDto responseDto = new UserDto();
                BeanUtils.copyProperties(user, responseDto);
                // Set role explicitly in the response DTO
                responseDto.setRole(user.getRole().name());
                // Add the generated token to the response DTO (assuming you have a token field in UserDto)
                responseDto.setToken(token);  // Ensure UserDto has a `token` field
                System.out.println("Login successful, JWT token generated: " + token);
                return responseDto;
            } else {
                throw new BadCredentialsException("Invalid password.");  // Use an exception
            }
        } else {
            throw new UsernameNotFoundException("User not found for email: " + userDto.getEmail());  // Use an exception
        }
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        // Log the email to see if the correct data is passed
        System.out.println("Attempting to update user with email: " + userDto.getEmail());
        Optional<User> userOptional = UserRepo.findByEmail(userDto.getEmail());
        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // Log that the user was found in the database
            System.out.println("User found for email: " + userDto.getEmail());
            // Check if the username is present before updating
            if (userDto.getUserName() != null && !userDto.getUserName().isEmpty()) {
                user.setUserName(userDto.getUserName());
                System.out.println("Updating username to: " + userDto.getUserName());
            }
            // Check if the email is present before updating
            if (userDto.getEmail() != null && !userDto.getEmail().isEmpty()) {
                user.setEmail(userDto.getEmail());
                System.out.println("Updating email to: " + userDto.getEmail());
            }
            // Attempt to save the updated entity
            try {
                User updatedEntity = UserRepo.save(user);
                System.out.println("User updated successfully with ID: " + updatedEntity.getUserId());

                // Return the updated entity as a DTO
                UserDto responseDto = new UserDto();
                BeanUtils.copyProperties(updatedEntity, responseDto);
                responseDto.setPassword(null);  // Exclude password
                responseDto.setRole(updatedEntity.getRole().name());  // Set the role in the DTO
                return responseDto;
            } catch (Exception e) {
                System.out.println("Error while saving updated user: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("User not found for email: " + userDto.getEmail());
        }

        return null;
    }

    @Override
    public UserDto getRole(UserDto userDto) {
        Optional<User> userOptional = UserRepo.findByEmail(userDto.getEmail());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            UserDto responseDto = new UserDto();
            responseDto.setUserName(user.getUserName());
            responseDto.setEmail(user.getEmail());
            // In this case, you may need to add role information to the UserDto
            responseDto.setRole(user.getRole().name());  // Assuming you add a role field in UserDto
            return responseDto;
        }
        System.out.println("User not found.");
        return null;
    }

    public UserDto promoteUserToAdmin(String email) {
        Optional<User> userOptional = UserRepo.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            // Check if the user is already an admin
            if (user.getRole() == Role.ADMIN) {
                // Throwing RuntimeException with the message "User is already an Admin."
                throw new RuntimeException("User is already an Admin.");
            }

            System.out.println("Current Role: " + user.getRole());
            user.setRole(Role.ADMIN);  // Set role to ADMIN
            System.out.println("New Role: " + user.getRole());  // Log new role
            User updatedUser = UserRepo.save(user);
            // Prepare response
            UserDto responseDto = new UserDto();
            BeanUtils.copyProperties(updatedUser, responseDto);
            responseDto.setRole(updatedUser.getRole().name());
            System.out.println("Role updated and saved in database.");  // Log after save

            return responseDto;
        } else {
            // If the user does not exist, throw an exception
            throw new RuntimeException("User not found with email: " + email);
        }
    }


    public boolean deactivateUser(String email) {
        Optional<User> userOptional = UserRepo.findByEmail(email);
        if (userOptional.isPresent()) {
            UserRepo.delete(userOptional.get());
            System.out.println("User deleted successfully.");
            return true;
        } else {
            System.out.println("User not found with email: " + email);
            return false;
        }
    }
    public List<UserDto> viewAllUsers() {
        List<User> users = UserRepo.findAll();  // Fetch all users from the database
        List<UserDto> userDtoList = new ArrayList<>();
        for (User user : users) {
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(user, userDto);  // Convert User entity to UserDto
            userDto.setPassword(null);  // Exclude password from the response
            userDtoList.add(userDto);
        }

        return userDtoList;
    }

}



