package com.springboot.blog.service.service.impl;

import com.springboot.blog.dto.*;
import com.springboot.blog.entity.RolesEntity;
import com.springboot.blog.entity.UserEntity;
import com.springboot.blog.exception.ResourceExistException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.repository.RoleRepository;
import com.springboot.blog.repository.UserRepository;
import com.springboot.blog.service.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private RoleRepository roleRepository;
    private ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper,RoleRepository roleRepository){
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.roleRepository = roleRepository;
    }

    private UserEntity findUserByUsername(String name){
        return this.userRepository.findByUsername(name).orElseThrow(()->new ResourceExistException("user with" +name+"exist"));
    }
    private UserEntity mapDtoToEntity(UpdateProfileDto updateProfileDto){
        return this.modelMapper.map(updateProfileDto,UserEntity.class);
    }
    private UpdateProfileDto mapEntityToDto(UserEntity user){
        return this.modelMapper.map(user,UpdateProfileDto.class);
    }
    @Override
    public UpdateProfileResponseDto updateProfile(UpdateProfileDto updateProfileDto, String id) {
        UserEntity user = this.findUserByUsername(id);
        if(updateProfileDto.getFirstname()!= null){
            user.setFirstname(updateProfileDto.getFirstname());
        }
        if(updateProfileDto.getLastname()!= null){
            user.setLastname(updateProfileDto.getLastname());
        }
        if(updateProfileDto.getImage()!= null){
            user.setImage(updateProfileDto.getImage());
        }
        if(updateProfileDto.getAge()!=0){
            user.setAge(updateProfileDto.getAge());
        }
        this.userRepository.save(user);
        UpdateProfileResponseDto response = new UpdateProfileResponseDto();
        response.setId(user.getId());
        response.setMessage("user updated successfully");
        return response;
    }

    @Override
    public GetProfileDtoResponse getProfile(String id) {
        UserEntity user = this.findUserByUsername(id);
        ProfileDto profile = this.modelMapper.map(user,ProfileDto.class);
        GetProfileDtoResponse response = new GetProfileDtoResponse();
        response.setProfile(profile);
        return response;
    }

    @Override
    public UpgradeToAdminResponseDto upgradeUserToAdmin(String id) {
        UpgradeToAdminResponseDto response = new UpgradeToAdminResponseDto();
        UserEntity user = this.findUserByUsername(id);
        Set<RolesEntity> roles = new HashSet<>(user.getRoles());
        System.out.println(user);
        boolean isAdmin = roles.stream().anyMatch(role->"ADMIN".equals(role.getName()));
        System.out.println(isAdmin);
        if(isAdmin) {
            response.setStatus(false);
            response.setMessage("user already an admin");
            return  response;
        }
        RolesEntity role = this.roleRepository.findByName("ADMIN").get();
        System.out.println(role);
        roles.add(role);
        user.setRoles(roles);
        this.userRepository.save(user);
        return response;
    }
}
