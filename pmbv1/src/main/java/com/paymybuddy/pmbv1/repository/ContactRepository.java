package com.mg.paymybuddy.repository;

import com.mg.paymybuddy.model.Contact;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Repository
public class ContactRepository {

    private List<Contact> contactList = new ArrayList<>();

    public Optional<List<Contact>> findById(int id){
        List<Contact> result = new ArrayList<>();
        for (Contact contact : contactList) {
            if (contact.getUserId() == id) {
                result.add(contact);
            }
        }
        return Optional.of(result);
    }

    public void save(Contact contact) {
        contactList.add(contact);
    }

}

