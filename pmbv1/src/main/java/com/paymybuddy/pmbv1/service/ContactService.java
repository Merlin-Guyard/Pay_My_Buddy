package com.paymybuddy.pmbv1.service;

import com.paymybuddy.pmbv1.model.User;
import com.paymybuddy.pmbv1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    SCHService schService;

    @Autowired
    private MessageService messageService;

    public String addContact(String email) throws RuntimeException {

        Optional<User> oUser = userRepository.findByEmail(schService.getName());
        if(oUser.isEmpty()) {
            throw new RuntimeException(messageService.returnMessage("err.unknown_user"));
        }
        User user = oUser.get();

        Optional<User> oContact = userRepository.findByEmail(email);
        if(oContact.isEmpty()) {
            throw new RuntimeException(messageService.returnMessage("err.unknown_contact"));
        }
        User contact = oContact.get();

        if (user.getUserId() == contact.getUserId()) {
            throw new RuntimeException(messageService.returnMessage("err.contact_is_user"));
        }

        boolean isAlreadyFriend = user.getFriendList().stream().anyMatch(friend -> friend.getEmail().equals(contact.getEmail()));
        if (isAlreadyFriend) {
            throw new RuntimeException(messageService.returnMessage("err.duplicate_contact"));
        }

        List<User> userContactList = user.getFriendList();
        userContactList.add(contact);
        user.setFriendList(userContactList);
        userRepository.save(user);

        return messageService.returnMessage("stat.add_contact");
    }

    public String removeContact(String email) throws RuntimeException {

        Optional<User> oUser = userRepository.findByEmail(schService.getName());
        if(oUser.isEmpty()) {
            throw new RuntimeException(messageService.returnMessage("err.unknown_user"));
        }
        User user = oUser.get();

        Optional<User> oContact = userRepository.findByEmail(email);
        if(oContact.isEmpty()) {
            throw new RuntimeException(messageService.returnMessage("err.unknown_contact"));
        }
        User contact = oContact.get();

        boolean isAlreadyFriend = user.getFriendList().stream().anyMatch(friend -> friend.getEmail().equals(contact.getEmail()));
        if (!isAlreadyFriend) {
            throw new RuntimeException(messageService.returnMessage("err.not_friend_contact"));
        }

        List<User> userContactList = user.getFriendList();
        userContactList.remove(contact);
        user.setFriendList(userContactList);
        userRepository.save(user);

        return messageService.returnMessage("stat.del_contact");
    }
}