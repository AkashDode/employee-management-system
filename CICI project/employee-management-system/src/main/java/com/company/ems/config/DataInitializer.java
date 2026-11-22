package com.company.ems.config;

import com.company.ems.entity.User;
import com.company.ems.repository.UserRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer
        implements CommandLineRunner {

    private final UserRepository repository;
    private final PasswordEncoder encoder;

    public DataInitializer(
            UserRepository repository,
            PasswordEncoder encoder) {

        this.repository = repository;
        this.encoder = encoder;
    }

    @Override
    public void run(String... args)
            throws Exception {

        if (repository.findByUsername("admin")
                .isEmpty()) {

            User admin =
                    new User();

            admin.setUsername("admin");

            admin.setEmail(
                    "admin@ems.com");

            admin.setPassword(
                    encoder.encode(
                            "Admin@123"));

            admin.setRole("ADMIN");

            repository.save(admin);

            System.out.println(
                    "✅ Default Admin Created");
        }
    }
}