package com.paymybuddy.pmbv1.controller;

import com.paymybuddy.pmbv1.model.User;
import com.paymybuddy.pmbv1.service.ContactService;
import com.paymybuddy.pmbv1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ContactController {

    @Autowired
    private ContactService contactService;

    @Autowired
    private UserService userService;

    @GetMapping("/contact")
    public String getContact(User user, Model model) {
        User user2 = userService.getUserByEmail();
        List<User> contacts = new ArrayList<>(user2.getFriendList());

        model.addAttribute("users", contacts);
        return "contact";
    }

    @GetMapping("/contact/del")
    public String removeUser(@RequestParam String userEmail, RedirectAttributes redirectAttributes) throws RuntimeException {

        try {
            String status = contactService.removeContact(userEmail);
            redirectAttributes.addFlashAttribute("del_status", status);
        }
        catch (RuntimeException exception) {
            String error = exception.getMessage();
            redirectAttributes.addFlashAttribute("del_error", error);
        }
        return "redirect:/contact";
    }

    @PostMapping("/contact/add")
    public String getUser(User user, RedirectAttributes redirectAttributes) throws RuntimeException {

            try {
                String status = contactService.addContact(user.getEmail());
                redirectAttributes.addFlashAttribute("add_status", status);
            }
            catch (RuntimeException exception) {
                String error = exception.getMessage();
                redirectAttributes.addFlashAttribute("add_error", error);
            }
            return "redirect:/contact";
        }
}
