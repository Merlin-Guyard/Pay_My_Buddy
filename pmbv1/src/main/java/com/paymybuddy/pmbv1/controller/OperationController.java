package com.paymybuddy.pmbv1.controller;

import com.paymybuddy.pmbv1.service.OperationService;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/operation")
public class OperationController {

    @Autowired
    OperationService operationService;

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