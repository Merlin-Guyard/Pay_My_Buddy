package com.mg.paymybuddy.service;

import com.mg.paymybuddy.model.User;
import com.mg.paymybuddy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {

    @Autowired
    private UserRepository userRepository;

    public void save(String firstName, String lastName, String email, String password){

    }
}
