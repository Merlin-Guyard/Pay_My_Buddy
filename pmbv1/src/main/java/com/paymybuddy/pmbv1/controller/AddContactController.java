package com.paymybuddy.pmbv1.controller;

import com.paymybuddy.pmbv1.service.ContactService;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/add_contact")
public class AddContactController {

    @Autowired
    ContactService contactService;

    @GetMapping
    public String getAddContact() {
        return "add_contact";
    }


}
