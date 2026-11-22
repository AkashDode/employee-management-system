package com.company.ems.controller;

import com.company.ems.entity.User;
import com.company.ems.exception.DuplicateUserException;
import com.company.ems.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage() {

        return "login";
    }

    @GetMapping("/signup")
    public String signupPage(Model model) {

        model.addAttribute(
                "user",
                new User());

        return "signup";
    }

    @PostMapping("/signup")
    public String registerUser(
            @ModelAttribute User user,
            Model model) {

        try {
            userService.registerUser(user);
        } catch (DuplicateUserException exception) {
            model.addAttribute(
                    "error",
                    exception.getMessage());
            return "signup";
        }

        return "redirect:/login";
    }
}