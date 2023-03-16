package com.paymybuddy.pmbv1.controller;

import com.paymybuddy.pmbv1.model.User;
import com.paymybuddy.pmbv1.repository.UserRepository;
import com.paymybuddy.pmbv1.service.UserService;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String getProfile(Model model) {
        Optional<User> oUser = userService.getUserByEmail();
        User user = oUser.get();

        model.addAttribute("user", user);
        return "profile";
    }

    @PostMapping
    public String getUser(Model model,
                          @NotNull String firstName,
                          @NotNull String lastName,
                          @NotNull String email) {

        //save user
        User user = new User(firstName, lastName, email);
        userService.updateUser(user);

        return "profile";
    }

}
