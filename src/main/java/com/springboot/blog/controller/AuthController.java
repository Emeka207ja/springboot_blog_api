package com.springboot.blog.controller;


import com.springboot.blog.dto.*;
import com.springboot.blog.entity.RolesEntity;
import com.springboot.blog.entity.UserEntity;
import com.springboot.blog.exception.ResourceExistException;
import com.springboot.blog.repository.RoleRepository;
import com.springboot.blog.repository.UserRepository;
import com.springboot.blog.security.JwtTokenProvider;
import com.springboot.blog.service.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    private UserRepository userRepository;

    private RoleRepository roleRepository;

    private PasswordEncoder passwordEncoder;

    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;
    private EmailService emailService;

    public AuthController(UserRepository userRepository,
                          RoleRepository roleRepository,
                          PasswordEncoder passwordEncoder,
                          AuthenticationManager authenticationManager,
                          JwtTokenProvider jwtTokenProvider,
                          EmailService emailService
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.emailService = emailService;
    }

    @PostMapping("/register")
    public ResponseEntity<signupResponseDto> RegisterUser(@Valid @RequestBody()UserDto userDto){
        if(this.userRepository.existsByUsername((userDto.getUsername()))){
            throw new ResourceExistException("username already taken");
        }
        UserEntity user = new UserEntity();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        RolesEntity role = this.roleRepository.findByName("USER").get();
        user.setRoles(Collections.singleton(role));
        userRepository.save(user);
        signupResponseDto response = new signupResponseDto();
        SignupMailDto mailDto = new SignupMailDto();
        mailDto.setUsername(userDto.getUsername());
        this.emailService.sendSignupMail(mailDto);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> loginUser(@Valid @RequestBody LoginDto loginDto) throws MessagingException {
        Authentication authentication = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(),loginDto.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        LoginResponseDto response = new LoginResponseDto();
        String  token = jwtTokenProvider.generateToken(authentication);
        response.setToken(token);
        sendLoginMailDto loginMailDto = new sendLoginMailDto();
        loginMailDto.setUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        this.emailService.sendLoginMail(loginMailDto);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
