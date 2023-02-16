package com.mg.paymybuddy.service;

import com.mg.paymybuddy.model.User;
import com.mg.paymybuddy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public User addUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(User user) {
        userRepository.deleteById(user.getUserId());
        return userRepository.save(user);
    }

    public void delUser(Integer id) {
        userRepository.deleteById(id);
    }
}
