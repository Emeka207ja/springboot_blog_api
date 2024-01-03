package com.springboot.blog.dto;

import lombok.Data;

@Data
public class GetProfileDtoResponse {
    private String message ="profile retrieved";
    private ProfileDto profile;
}
