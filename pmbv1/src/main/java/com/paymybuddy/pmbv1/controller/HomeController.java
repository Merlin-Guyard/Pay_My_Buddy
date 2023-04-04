package com.paymybuddy.pmbv1.controller;

import com.paymybuddy.pmbv1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/home")
    public String getHome() {

        return "home";
    }
}
