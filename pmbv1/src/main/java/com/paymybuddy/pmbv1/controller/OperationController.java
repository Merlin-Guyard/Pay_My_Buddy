package com.paymybuddy.pmbv1.controller;

import com.paymybuddy.pmbv1.model.User;
import com.paymybuddy.pmbv1.service.ContactService;
import com.paymybuddy.pmbv1.service.OperationService;
import com.paymybuddy.pmbv1.service.UserService;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class OperationController {

    @Autowired
    OperationService operationService;

    @Autowired
    ContactService contactService;

    @Autowired
    UserService userService;

    @GetMapping("/operation")
    public String getTransfer(Model model) {
        User user = userService.getUserByEmail();
        List<User> contacts = new ArrayList<>(user.getFriendList());

        model.addAttribute("users", contacts);
        return "operation";
    }

    @RequestMapping("/operation/transfer")
    public String transferMoney(Model model,
                                @NotNull String email,
                                @NotNull int amount) {

        System.out.println(operationService.send(email, amount));
        return "redirect:/operation";
    }

}