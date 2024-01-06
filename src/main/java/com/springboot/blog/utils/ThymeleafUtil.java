package com.springboot.blog.utils;

import com.springboot.blog.dto.WelcomeMessageDto;

import java.util.HashMap;
import java.util.Map;

public class ThymeleafUtil {
    public static Map<String, Object> convertModel(WelcomeMessageDto model) {
        Map<String,Object> map = new HashMap<>();
        map.put("name",model.getName());
        return map;
    }
}
