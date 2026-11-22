package com.company.ems.controller;

import com.company.ems.entity.User;
import com.company.ems.repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final UserRepository repository;
    private final PasswordEncoder encoder;

    public AuthController(
            UserRepository repository,
            PasswordEncoder encoder) {

        this.repository = repository;
        this.encoder = encoder;
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

        if(repository.findByUsername(
                user.getUsername()).isPresent()) {

            model.addAttribute(
                    "error",
                    "Username already exists");

            return "signup";
        }

        user.setPassword(
                encoder.encode(
                        user.getPassword()));

        user.setRole("USER");

        repository.save(user);

        return "redirect:/login";
    }
}