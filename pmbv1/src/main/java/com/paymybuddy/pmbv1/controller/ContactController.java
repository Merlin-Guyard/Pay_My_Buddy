package com.paymybuddy.pmbv1.configuration.controller;

import com.mg.paymybuddy.service.ContactService;
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
                        @NotNull String email) {

        contactService.addContact(email);
    }

}
