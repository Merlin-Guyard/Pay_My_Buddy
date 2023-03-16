package com.paymybuddy.pmbv1.controller;

import com.paymybuddy.pmbv1.model.User;
import com.paymybuddy.pmbv1.service.UserService;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String getRegister() {
        return "register_form";
    }

    @PostMapping
    public String getUser(Model model,
                          @NotNull String firstName,
                          @NotNull String lastName,
                          @NotNull String email,
                          @NotNull String password) {

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        //save user
        User user = new User(firstName, lastName, email, bCryptPasswordEncoder.encode(password));
        userService.addUser(user);

        return "login";
    }

}