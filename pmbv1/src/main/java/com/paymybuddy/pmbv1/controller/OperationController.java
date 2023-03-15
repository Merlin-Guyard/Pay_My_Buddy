package com.paymybuddy.pmbv1.controller;

import com.paymybuddy.pmbv1.model.User;
import com.paymybuddy.pmbv1.repository.UserRepository;
import com.paymybuddy.pmbv1.service.OperationService;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/operation")
public class OperationController {

    @Autowired
    OperationService operationService;

    @Autowired
    UserRepository userRepository;

    @GetMapping
    public String getTransfer() {
        return "operation";
    }

    @PostMapping
    public void transferMoney(Model model,
                              @NotNull String email,
                              @NotNull int amount) {

        System.out.println(operationService.send(email, amount));
    }

    @RequestMapping(value = "user", method = RequestMethod.GET)
    public String users(Model model) {
        List<User> toto = userRepository.findAll();
        model.addAttribute("users", toto );
//        String
//        if (hasError) {
//            model.addAttribute("errorAmount", message)
//        }
        return "user/list";
    }

//    @PostMapping
//    public void addMoney(Model model,
//                              @NotNull int amount) {
//
////        operationService.send(contact, amount);
//    }
//
//    @PostMapping
//    public void retrieveMoney(Model model,
//                              @NotNull int amount) {
//
////        operationService.send(contact, amount);
//    }
}