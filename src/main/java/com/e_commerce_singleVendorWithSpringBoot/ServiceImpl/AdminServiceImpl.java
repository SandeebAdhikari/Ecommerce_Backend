package com.e_commerce_singleVendorWithSpringBoot.ServiceImpl;

import com.e_commerce_singleVendorWithSpringBoot.Dto.AdminDto;
import com.e_commerce_singleVendorWithSpringBoot.Entity.Admin;
import com.e_commerce_singleVendorWithSpringBoot.Entity.Role;
import com.e_commerce_singleVendorWithSpringBoot.Repository.AdminRepository;
import com.e_commerce_singleVendorWithSpringBoot.Repository.UserRepository;
import com.e_commerce_singleVendorWithSpringBoot.Services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.NoSuchElementException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private UserRepository userRepository;
    private Object adminDto;


    @Override
    public AdminDto promoteUserToAdmin(String email) {
        var user = userRepository.findByEmail(email).orElseThrow(() -> new NoSuchElementException("User not found with email: " + email));

        Admin admin = new Admin(
                user.getUserId(),
                user.getUserName(),
                user.getEmail(),
                user.getPassword(),
                Role.ADMIN,
                "Default Department",
                false
        );

        adminRepository.save(admin);

        return new AdminDto(
                admin.getUserId(),
                admin.getUserName(),
                admin.getEmail(),
                admin.getPassword(),
                admin.getRole().name(),
                admin.getDepartment(),
                admin.isHasSuperAdminAccess()
        );
    }

    @Override
    public List<AdminDto> getAllAdmins() {
        List<Admin> admins = adminRepository.findAll();

        return admins.stream()
                .map(admin -> new AdminDto(
                        admin.getUserId(),
                        admin.getUserName(),
                        admin.getEmail(),
                        admin.getPassword(),
                        admin.getRole().name(),
                        admin.getDepartment(),
                        admin.isHasSuperAdminAccess()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public AdminDto getAdminById(long adminId) {
        // Use adminId to find the admin entity
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new NoSuchElementException("Admin not found with ID: " + adminId));

        // Return AdminDto
        return new AdminDto(
                admin.getUserId(),
                admin.getUserName(),
                admin.getEmail(),
                admin.getPassword(),
                admin.getRole().name(),
                admin.getDepartment(),
                admin.isHasSuperAdminAccess()
        );
    }



    @Override
    public AdminDto updateAdmin(AdminDto adminDto) {

        Admin admin = adminRepository.findById(adminDto.getUserId())
                .orElseThrow(() -> new NoSuchElementException("Admin not found with ID: " + adminDto.getUserId()));

        admin.setUserName(adminDto.getUserName());
        admin.setEmail(adminDto.getEmail());
        admin.setPassword(adminDto.getPassword());

        admin.setHasSuperAdminAccess(adminDto.isAdminPermissions());

        adminRepository.save(admin);

        return adminDto;
    }

    @Override
    public boolean deactivateAdmin(String email) {
        Admin admin = adminRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("Admin not found with email: " + email));
        adminRepository.delete(admin);

        return true;
    }
}


