package com.springboot.blog.service.service;

import com.springboot.blog.dto.SignupMailDto;
import com.springboot.blog.dto.sendLoginMailDto;
import jakarta.mail.MessagingException;
import org.springframework.scheduling.annotation.Async;

public interface EmailService {
     void sendLoginMail(sendLoginMailDto loginMailDto) ;
     void sendSignupMail(SignupMailDto signupMailDto);
}
