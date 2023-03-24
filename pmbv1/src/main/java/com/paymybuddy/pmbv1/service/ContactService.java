package com.paymybuddy.pmbv1.service;


import com.paymybuddy.pmbv1.model.User;
import com.paymybuddy.pmbv1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageService messageService;

    public String addContact(String email) throws Throwable {
        if(true) {
            throw new Throwable(messageService.returnMessage("err.duplicate_user"));
        }

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
            return messageService.returnMessage("err.contact_is_user");
        }

        boolean isAlreadyFriend = user.getFriendList().stream().anyMatch(friend -> friend.getEmail().equals(contact.getEmail()));
        if (isAlreadyFriend) {
            return messageService.returnMessage("err.duplicate_contact");
        }

        List<User> userContactList = user.getFriendList();
        userContactList.add(contact);
        user.setFriendList(userContactList);
        userRepository.save(user);

        return messageService.returnMessage("stat.add_contact");
    }

    public String removeContact(String email) {

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

        boolean isAlreadyFriend = user.getFriendList().stream().anyMatch(friend -> friend.getEmail().equals(contact.getEmail()));
        if (!isAlreadyFriend) {
            return messageService.returnMessage("err.not_friend_contact");
        }

        List<User> userContactList = user.getFriendList();
        userContactList.remove(contact);
        user.setFriendList(userContactList);
        userRepository.save(user);

        return messageService.returnMessage("stat.del_contact");
    }
}