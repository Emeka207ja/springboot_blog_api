package com.springboot.blog.service.service;

import com.springboot.blog.dto.GetProfileDtoResponse;
import com.springboot.blog.dto.UpdateProfileDto;
import com.springboot.blog.dto.UpdateProfileResponseDto;
import com.springboot.blog.dto.UpgradeToAdminResponseDto;

public interface UserService {
    UpdateProfileResponseDto updateProfile(UpdateProfileDto updateProfileDto,String id);
    GetProfileDtoResponse getProfile(String id);
    UpgradeToAdminResponseDto upgradeUserToAdmin(String id);
}
