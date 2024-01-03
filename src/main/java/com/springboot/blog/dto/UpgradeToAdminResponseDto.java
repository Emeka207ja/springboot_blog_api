package com.springboot.blog.dto;

import lombok.Data;

@Data
public class UpgradeToAdminResponseDto {
    private String message = "user upgraded to admin";
    private Boolean status = true;
}
