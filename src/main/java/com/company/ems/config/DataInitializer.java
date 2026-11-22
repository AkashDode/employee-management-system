package com.company.ems.config;

import com.company.ems.entity.User;
import com.company.ems.repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer
        implements CommandLineRunner {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(DataInitializer.class);

    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final String initialAdminPassword;

    public DataInitializer(
            UserRepository repository,
            PasswordEncoder encoder,
            @Value("${ems.admin.initial-password:}")
            String initialAdminPassword) {

        this.repository = repository;
        this.encoder = encoder;
        this.initialAdminPassword = initialAdminPassword;
    }

    @Override
    public void run(String... args) {
        if (repository.findByUsername("admin")
                .isEmpty()) {
            if (initialAdminPassword.isBlank()) {
                throw new IllegalStateException(
                        "Set EMS_ADMIN_INITIAL_PASSWORD before starting the application to create the initial admin account.");
            }

            User admin =
                    new User();

            admin.setUsername("admin");

            admin.setEmail(
                    "admin@ems.com");

            admin.setPassword(
                    encoder.encode(
                            initialAdminPassword));

            admin.setRole("ADMIN");

            repository.save(admin);

            LOGGER.info("Initial admin account created.");
        }
    }
}