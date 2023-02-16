package com.paymybuddy.pmbv1.controller;

import com.paymybuddy.pmbv1.service.ContactService;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/contact")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @GetMapping
    public String getContact() {
            return "contact";
        }

    @PostMapping
    public void addUser(Model model,
                        @NotNull String emailAdd) {

        System.out.println(contactService.addContact(emailAdd));
    }

    @GetMapping(value = "/del")
    public String removeUser(Model model,
                        @NotNull String emailDel) {

        System.out.println(contactService.removeContact(emailDel));
        return "home";
    }

}
