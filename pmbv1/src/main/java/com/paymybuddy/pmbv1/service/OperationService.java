package com.paymybuddy.pmbv1.service;

import com.paymybuddy.pmbv1.model.User;
import com.paymybuddy.pmbv1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OperationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageService messageService;

    public String send(String email, int amount) {

        Optional<User> oUser = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if(oUser.isEmpty()) {
            return messageService.returnMessage("err.unknown_user");
        }
        User user = oUser.get();

        Optional<User> oContact = userRepository.findByEmail(email);
        if(oContact.isEmpty()) {
            return messageService.returnMessage("err.unknown_contact");
        }
        User contact = oContact.get();

        if (user.getUserId() == contact.getUserId()) {
            return messageService.returnMessage("err.send_to_self");
        }

        boolean isAlreadyFriend = user.getFriendList().stream().anyMatch(friend -> friend.getEmail().equals(contact.getEmail()));
        if (!isAlreadyFriend) {
            return messageService.returnMessage("err.not_friend_contact");
        }

        if (user.getBalance()<0) {
            return messageService.returnMessage("err.operation");
        }

        if (user.getBalance()-amount<0) {
            return messageService.returnMessage("err.insufficient_funds");
        }

        user.setBalance(user.getBalance()-amount);
        contact.setBalance(contact.getBalance()+amount);
        userRepository.save(user);
        userRepository.save(contact);

        return messageService.returnMessage("stat.transfer");
    }
}
