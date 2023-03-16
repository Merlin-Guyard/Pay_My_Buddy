package com.paymybuddy.pmbv1.service;

import com.paymybuddy.pmbv1.model.User;
import com.paymybuddy.pmbv1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public Iterable<User> getUsers(){
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Integer id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByEmail() {
        return userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    public Iterable<User> getContacts(){
        List<User> contacts = new ArrayList<>();
        userRepository.findAll();
        return contacts;
    }

    public User addUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(User user) {
        User user2Update = userRepository.getById(user.getUserId());
        user2Update.setFirstName(user.getFirstName());
        user2Update.setLastName(user.getLastName());
        user2Update.setEmail(user.getEmail());
        userRepository.deleteById(user2Update.getUserId());
        return userRepository.save(user2Update);
    }

    public void delUser(Integer id) {
        userRepository.deleteById(id);
    }
}