

package com.paymybuddy.pmbv1.controller;

import com.paymybuddy.pmbv1.model.User;
import com.paymybuddy.pmbv1.service.ContactService;
import com.paymybuddy.pmbv1.service.UserService;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class ContactController {

    @Autowired
    private ContactService contactService;

    @Autowired
    private UserService userService;

    @GetMapping("/contact")
    public String getContact(Model model) {
        Optional<User> oUser = userService.getUserByEmail();
        User user = oUser.get();
        List<User> contacts = new ArrayList<>(user.getFriendList());


        model.addAttribute("users", contacts);
        return "contact";
    }

//    @PostMapping
//    public void addUser(Model model,
//                        @NotNull String emailAdd) {
//
//        System.out.println(contactService.addContact(emailAdd));
//    }

    @GetMapping("/contact/del")
    public String removeUser(@RequestParam String userEmail) {

        System.out.println(contactService.removeContact(userEmail));
        return "redirect:/contact";
    }

//    @GetMapping(value = "/del")
//    public String removeUser(@RequestParam String userEmail) {
//
//        System.out.println(contactService.removeContact(userEmail));
//        return "home";
//    }


}
