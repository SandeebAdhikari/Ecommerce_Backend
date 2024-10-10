package com.e_commerce_singleVendorWithSpringBoot.Services;

import com.e_commerce_singleVendorWithSpringBoot.Dto.AdminDto;
import com.e_commerce_singleVendorWithSpringBoot.Entity.Admin;

import java.util.List;

public interface AdminService {

    // Promote a user to Admin
    AdminDto promoteUserToAdmin(String email);

    // Get a list of all Admins
    List<AdminDto> getAllAdmins();

    // Get Admin by ID
    AdminDto getAdminById(long adminId);

    // Update Admin details
    AdminDto updateAdmin(AdminDto adminDto);

    // Deactivate (delete) an Admin by email
    boolean deactivateAdmin(String email);
}
