package com.company.ems.service;

import com.company.ems.entity.User;
import com.company.ems.exception.DuplicateUserException;
import com.company.ems.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository repository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void registerUserNormalizesCredentialsAndAssignsUserRole() {
        User user = new User();
        user.setUsername("  alice  ");
        user.setEmail(" Alice@Example.com ");
        user.setPassword("plain-password");

        when(repository.existsByUsername("alice")).thenReturn(false);
        when(repository.existsByEmailIgnoreCase("alice@example.com")).thenReturn(false);
        when(passwordEncoder.encode("plain-password")).thenReturn("encoded-password");
        when(repository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User saved = userService.registerUser(user);

        assertEquals("alice", saved.getUsername());
        assertEquals("alice@example.com", saved.getEmail());
        assertEquals("encoded-password", saved.getPassword());
        assertEquals("USER", saved.getRole());
        verify(repository).save(user);
    }

    @Test
    void registerUserRejectsDuplicateUsername() {
        User user = new User();
        user.setUsername("alice");
        user.setEmail("alice@example.com");

        when(repository.existsByUsername("alice")).thenReturn(true);

        assertThrows(DuplicateUserException.class, () -> userService.registerUser(user));
        verify(repository, never()).save(any(User.class));
    }

    @Test
    void registerUserRejectsDuplicateEmailCaseInsensitively() {
        User user = new User();
        user.setUsername("alice");
        user.setEmail("Alice@Example.com");

        when(repository.existsByUsername("alice")).thenReturn(false);
        when(repository.existsByEmailIgnoreCase("alice@example.com")).thenReturn(true);

        assertThrows(DuplicateUserException.class, () -> userService.registerUser(user));
        verify(repository, never()).save(any(User.class));
    }
}
