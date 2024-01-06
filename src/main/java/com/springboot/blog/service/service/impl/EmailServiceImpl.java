package com.springboot.blog.service.service.impl;

import com.springboot.blog.dto.SignupMailDto;
import com.springboot.blog.dto.WelcomeMessageDto;
import com.springboot.blog.dto.sendLoginMailDto;
import com.springboot.blog.entity.UserEntity;
import com.springboot.blog.exception.ResourceExistException;
import com.springboot.blog.repository.UserRepository;
import com.springboot.blog.service.service.EmailService;
import com.springboot.blog.utils.ThymeleafUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.codec.Utf8;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Arrays;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    UserRepository userRepository;
    @Value("spring.mail.username")
    String email;
    @Override
    @Async()
    public void sendLoginMail(sendLoginMailDto loginMailDto)  {
        try{
            UserEntity user = this.findUserByName(loginMailDto.getUsername());
            Context context = new Context();
            context.setVariable("name",loginMailDto.getUsername());
            String text = templateEngine.process("emails/loginMailTemplate",context);
            MimeMessage message = this.getMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,true,"UTF-8" );
            helper.setPriority(1);
            helper.setSubject("Login alert");
            helper.setFrom(email);
            helper.setTo(user.getEmail());
            helper.setText(text,true);
            javaMailSender.send(message);
        }catch(Exception e){
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

    @Override
    @Async
    public void sendSignupMail(SignupMailDto mailDto) {
        try{
            UserEntity user = this.findUserByName(mailDto.getUsername());
            Context context = new Context();
            context.setVariable("name", user.getUsername());
            String text = templateEngine.process("emails/SignupMailTemplate",context);
            MimeMessage message = this.getMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,true,"UTF-8");
            helper.setFrom(email);
            helper.setSubject("account created");
            helper.setTo(user.getEmail());
            helper.setText(text,true);
            javaMailSender.send(message);
        }catch (Exception e){
            System.out.println(e.getMessage());

        }
    }

    private MimeMessage getMimeMessage(){
        return javaMailSender.createMimeMessage();
    }
    private UserEntity findUserByName(String username){
        return this.userRepository.findByUsername(username).orElseThrow(()->new ResourceExistException("user doe not exist"));
    }
}
