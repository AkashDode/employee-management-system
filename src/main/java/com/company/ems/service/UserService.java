package com.company.ems.service;

import com.company.ems.entity.User;
import com.company.ems.exception.DuplicateUserException;
import com.company.ems.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Service
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository repository,
            PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User registerUser(User user) {
        String username = user.getUsername().trim();
        String email = user.getEmail().trim().toLowerCase(Locale.ROOT);

        if (repository.existsByUsername(username)) {
            throw new DuplicateUserException("Username already exists");
        }
        if (repository.existsByEmailIgnoreCase(email)) {
            throw new DuplicateUserException("Email already exists");
        }

        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("USER");

        return repository.save(user);
    }
}