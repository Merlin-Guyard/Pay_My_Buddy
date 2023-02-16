package com.paymybuddy.pmbv1.configuration.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/transfer")
public class OperationController {

    @GetMapping
    public String getTransfer() {
        return "transfer";
    }
}