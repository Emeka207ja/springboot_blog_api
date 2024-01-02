package com.springboot.blog.controller;


import com.springboot.blog.dto.LoginDto;
import com.springboot.blog.dto.LoginResponseDto;
import com.springboot.blog.dto.UserDto;
import com.springboot.blog.dto.signupResponseDto;
import com.springboot.blog.entity.RolesEntity;
import com.springboot.blog.entity.UserEntity;
import com.springboot.blog.exception.ResourceExistException;
import com.springboot.blog.repository.RoleRepository;
import com.springboot.blog.repository.UserRepository;
import com.springboot.blog.security.JwtTokenProvider;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public class UserController {

    private UserRepository userRepository;

    private RoleRepository roleRepository;

    private PasswordEncoder passwordEncoder;

    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;

    public UserController(UserRepository userRepository,
                          RoleRepository roleRepository,
                          PasswordEncoder passwordEncoder,
                          AuthenticationManager authenticationManager,
                          JwtTokenProvider jwtTokenProvider
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
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
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> loginUser(@Valid @RequestBody LoginDto loginDto){
        Authentication authentication = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(),loginDto.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        LoginResponseDto response = new LoginResponseDto();
        String  token = jwtTokenProvider.generateToken(authentication);
        response.setToken(token);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
