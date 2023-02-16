package com.paymybuddy.pmbv1.configuration.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {

    @GetMapping
    public String getLogin() {
        return "login";
    }

//    @PostMapping
//    public String getUser(Model model,
//                          @NotNull String email,
//                          @NotNull String password, Principal principal) {
//
////        System.out.println(email);
//        return "profile";
//    }
}