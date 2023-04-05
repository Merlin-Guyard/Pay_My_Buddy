package com.paymybuddy.pmbv1.controller;

import com.paymybuddy.pmbv1.model.User;
import com.paymybuddy.pmbv1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class RegisterController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String getRegister(User user) {
        return "register";
    }

    @PostMapping("/register")
    public String getUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model) throws Throwable {

        if(bindingResult.hasErrors()){
            return "register";
        }

        try {
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            User user2Add = new User(user.getFirstName(), user.getLastName(), user.getEmail(), bCryptPasswordEncoder.encode(user.getPassword()));
            userService.addUser(user2Add);
            return "login";
        } catch (Throwable throwable) {
            String err = throwable.getMessage();
            ObjectError error = new ObjectError("globalError", err);
            bindingResult.addError(error);
            return "register";
        }
    }


}