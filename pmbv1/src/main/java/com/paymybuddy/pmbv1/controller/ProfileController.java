package com.paymybuddy.pmbv1.controller;

import com.paymybuddy.pmbv1.model.User;
import com.paymybuddy.pmbv1.service.OperationService;
import com.paymybuddy.pmbv1.service.UserService;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ProfileController {

    @Autowired
    private UserService userService;

    @Autowired
    private OperationService operationService;

    @GetMapping("/profile")
    public String getProfile(Model model) {
        User user = userService.getUserByEmail();

        model.addAttribute("user", user);
        return "profile";
    }

    @RequestMapping("/profile/bank")
    public String manageMoney(Model model, @NotNull String operation, @NotNull int amount, RedirectAttributes redirectAttributes) throws RuntimeException {

        try {
            String status = operationService.manage(operation, amount);
            redirectAttributes.addFlashAttribute("man_status", status);
        }
        catch (RuntimeException exception) {
            String error = exception.getMessage();
            redirectAttributes.addFlashAttribute("man_error", error);
        }
        return "redirect:/profile";
    }
}
