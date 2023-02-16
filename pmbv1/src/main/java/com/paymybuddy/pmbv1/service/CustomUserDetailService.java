package com.paymybuddy.pmbv1.service;

import com.mg.paymybuddy.model.User;
import com.mg.paymybuddy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    public UserDetails loadUserByUsername(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isEmpty()) {
            throw new UsernameNotFoundException("User " +email+ " not found");
        }
        return new org.springframework.security.core.userdetails.User(user.get().getEmail(), user.get().getPassword(), Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
    }

}
