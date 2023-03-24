package com.paymybuddy.pmbv1.service;

import com.paymybuddy.pmbv1.model.User;
import com.paymybuddy.pmbv1.repository.UserRepository;
import com.sun.xml.bind.v2.TODO;
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

    @Autowired
    private MessageService messageService;

    public User getUserByEmail() {

        Optional<User> oUser = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

        //TODO
        if(oUser.isEmpty()) {
            return oUser.get();
        }
        return oUser.get();
    }

    public Iterable<User> getContacts(){
        List<User> contacts = new ArrayList<>();
        userRepository.findAll();
        return contacts;
    }

    public String addUser(User user) throws Throwable {
        List<User> users = userRepository.findAll();

        //TODO: errors/status feedback
        for (User user2check : users)
        {
            if(user2check.getEmail().equals(user.getEmail())){
                throw new RuntimeException(messageService.returnMessage("err.duplicate_user"));
            }
        }

        userRepository.save(user);
        return messageService.returnMessage("stat.register");
    }
}