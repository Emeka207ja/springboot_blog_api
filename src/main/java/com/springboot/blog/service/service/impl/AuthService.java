package com.springboot.blog.service.service.impl;

import com.springboot.blog.entity.RolesEntity;
import com.springboot.blog.entity.UserEntity;
import com.springboot.blog.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AuthService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
//    RoleRepository roleRepository;
//    PasswordEncoder passwordEncoder;
//    AuthenticationManager authenticationManager;

//    public UserServiceImpl(UserRepository userRepository,
//                           PasswordEncoder passwordEncoder,
//                           AuthenticationManager authenticationManager,
//                           RoleRepository roleRepository
//    ) {
//        this.userRepository = userRepository;
//        this.passwordEncoder = passwordEncoder;
//        this.authenticationManager = authenticationManager;
//        this.roleRepository = roleRepository;
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = this.userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("invalid username or password"));
        return new User(user.getUsername(),user.getPassword(),mapRolesToAuthority(user.getRoles()));
    }
    private Collection <? extends GrantedAuthority> mapRolesToAuthority(Set<RolesEntity> roles){
            return roles.stream().map(role-> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());

    }

//    String registerUser(UserDto userDto){
//       if(this.userRepository.existsByUsername((userDto.getUsername()))){
//           throw new ResourceExistException("username already taken");
//       }
//       UserEntity user = new UserEntity();
//       user.setUsername(userDto.getUsername());
//       user.setEmail(userDto.getEmail());
//       user.setPassword(passwordEncoder.encode(userDto.getPassword()));
//       RolesEntity role = this.roleRepository.findByName("USER").get();
//       user.setRoles(Collections.singleton(role));
//       U
//        return "";
//    }
}
