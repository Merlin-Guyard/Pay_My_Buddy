package com.paymybuddy.pmbv1.controller;

import com.paymybuddy.pmbv1.model.User;
import com.paymybuddy.pmbv1.service.UserService;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

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
    public String getUser(@Valid Model model,
                          @NotNull String firstName,
                          @NotNull String lastName,
                          @NotNull String email,
                          @NotNull String password) {

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        //save user
        User user = new User(firstName, lastName, email, bCryptPasswordEncoder.encode(password));

        try {
            userService.addUser(user);
            return "login";
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return "register_form";
        }


    }

}