package com.paymybuddy.pmbv1.controller;

import com.paymybuddy.pmbv1.model.Operation;
import com.paymybuddy.pmbv1.model.User;
import com.paymybuddy.pmbv1.service.OperationService;
import com.paymybuddy.pmbv1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class OperationController {

    @Autowired
    OperationService operationService;

    @Autowired
    UserService userService;

    @GetMapping("/operation")
    public String getTransfer(Model model, RedirectAttributes redirectAttributes) throws RuntimeException {
        User user = userService.getUserByEmail();
        List<User> contacts = new ArrayList<>(user.getFriendList());

        try {
            List<Operation> operations = operationService.getOperations();
            model.addAttribute("users", contacts);
            model.addAttribute("operations", operations);
        }
        catch (RuntimeException exception) {
            String error = exception.getMessage();
            redirectAttributes.addFlashAttribute("fetch_error", error);
        }
        return "operation";
    }

    @RequestMapping("/operation/transfer")
    public String transferMoney(Model model, String email, String description, Optional<Integer> amount, RedirectAttributes redirectAttributes) throws RuntimeException {

        if (amount.isEmpty()){
            throw new RuntimeException();
        }

        try {
            String status = operationService.send(email, description, amount.get());
            redirectAttributes.addFlashAttribute("ope_status", status);
        }
        catch (RuntimeException exception) {
            String error = exception.getMessage();
            redirectAttributes.addFlashAttribute("ope_error", error);
        }
        return "redirect:/operation";
    }

}