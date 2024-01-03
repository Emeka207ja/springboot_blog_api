package com.springboot.blog.dto;

import lombok.Data;

import java.util.Set;
@Data
public class ProfileDto {
    private Long id;
    private  String email;
    private String username;
    private String firstname;
    private String lastname;
    private String image;
    private int age;
    Set<RoleDto>roles;
}
