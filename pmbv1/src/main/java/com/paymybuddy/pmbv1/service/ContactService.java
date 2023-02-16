package com.mg.paymybuddy.service;

import com.mg.paymybuddy.model.Contact;
import com.mg.paymybuddy.model.User;
import com.mg.paymybuddy.repository.ContactRepository;
import com.mg.paymybuddy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactService {

        @Autowired
        private ContactRepository contactRepository;

        @Autowired
        private UserRepository userRepository;

        public String addContact(String email2Add){


            String messageResult = "";

            Optional<User> user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
            Optional<User> contact2Add = userRepository.findByEmail(email2Add);
            Optional<List<Contact>> contactList = contactRepository.findById(user.get().getUserId());

            for (Contact contact : contactList.get()) {
                if (contact.getContactId() == contact2Add.get().getUserId()) {
                    messageResult = "Contact déjà enregistré";
                }
            }
            if (messageResult.isEmpty()) {
                Contact contact2save = new Contact(user.get().getUserId(), contact2Add.get().getUserId());
                contactRepository.save(contact2save);
                messageResult = "Contact enregistré";
            }

            return messageResult;
        }

}
