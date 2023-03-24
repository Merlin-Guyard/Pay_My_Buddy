

package com.paymybuddy.pmbv1.controller;

import com.paymybuddy.pmbv1.model.User;
import com.paymybuddy.pmbv1.service.ContactService;
import com.paymybuddy.pmbv1.service.UserService;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public String getContact(User user, Model model) {
        User user2 = userService.getUserByEmail();
        //TODO : get contact
        List<User> contacts = new ArrayList<>(user2.getFriendList());

        model.addAttribute("users", contacts);
        return "contact";
    }

    @GetMapping("/contact/del")
    public String removeUser(@RequestParam String userEmail) {

        System.out.println(contactService.removeContact(userEmail));
        return "redirect:/contact";
    }

//    @RequestMapping(value="/contact/add", method= RequestMethod.POST)
//    public String addUser(Model model,
//                          @NotNull String emailAdd) {
//
//        System.out.println(contactService.addContact(emailAdd));
//        return "redirect:/contact";
//    }

    @PostMapping("/contact/add")
    public String getUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model) throws Throwable {

        if(bindingResult.hasErrors()){
            return "contact";
        }

        try {
            contactService.addContact(user.getEmail());
            return "redirect:/contact";
        } catch (Throwable throwable) {
            String err = throwable.getMessage();
            ObjectError error = new ObjectError("globalError", err);
            bindingResult.addError(error);
            return "contact";
        }
    }
    
}
