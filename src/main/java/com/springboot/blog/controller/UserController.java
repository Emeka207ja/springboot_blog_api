package com.springboot.blog.controller;

import com.springboot.blog.dto.GetProfileDtoResponse;
import com.springboot.blog.dto.UpdateProfileDto;
import com.springboot.blog.dto.UpdateProfileResponseDto;
import com.springboot.blog.dto.UpgradeToAdminResponseDto;
import com.springboot.blog.service.service.UserService;
import com.springboot.blog.service.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PutMapping("/profile")
    public ResponseEntity<UpdateProfileResponseDto> updateProfile(@RequestBody()UpdateProfileDto updateProfileDto){
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        return new ResponseEntity<>(this.userService.updateProfile(updateProfileDto,user), HttpStatus.CREATED);
    }
    @GetMapping("/profile")
    public ResponseEntity<GetProfileDtoResponse> getProfile(){
        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(this.userService.getProfile(id));
    }
    @PutMapping("/upgrade")
    public ResponseEntity<UpgradeToAdminResponseDto> upgradeUseToAdmin(){
        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        return new ResponseEntity<>(this.userService.upgradeUserToAdmin(id),HttpStatus.CREATED );
    }
}
