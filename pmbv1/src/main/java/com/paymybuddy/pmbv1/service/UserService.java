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

    @Autowired
    SCHService schService;

    public User getUserByEmail() {

        Optional<User> oUser = userRepository.findByEmail(schService.getName());

        if (oUser.isPresent()) {
            User user = oUser.get();
//            user.getFriendList().size(); // Access the friend list to initialize it
            return user;
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public Iterable<User> getContacts(){
        List<User> contacts = new ArrayList<>();
        userRepository.findAll();
        return contacts;
    }

    public String addUser(User user) throws RuntimeException {
        List<User> users = userRepository.findAll();

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