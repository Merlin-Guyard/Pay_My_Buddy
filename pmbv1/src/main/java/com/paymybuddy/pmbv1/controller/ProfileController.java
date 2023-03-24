package com.paymybuddy.pmbv1.controller;

import com.paymybuddy.pmbv1.model.User;
import com.paymybuddy.pmbv1.repository.UserRepository;
import com.paymybuddy.pmbv1.service.OperationService;
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
public class ProfileController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OperationService operationService;

    @GetMapping("/profile")
    public String getProfile(Model model) {
        User user = userService.getUserByEmail();

        model.addAttribute("user", user);
        return "profile";
    }

    @RequestMapping("/profile/bank")
    public String manageMoney(Model model,
                              @NotNull String operation,
                              @NotNull int amount) throws Throwable {

        operationService.manage(operation, amount);
        return "redirect:/profile";
    }

}
