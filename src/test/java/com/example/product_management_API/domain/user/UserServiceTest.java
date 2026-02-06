package com.example.product_management_API.domain.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.persistence.EntityExistsException;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository repository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService service;

    @Test
    void register_ShouldCreateUser_WhenEmailIsUnique() {
        // Arrange
        String name = "Test User";
        String email = "test@example.com";
        String password = "password";
        Role role = Role.USER;

        when(repository.existsByEmail(email)).thenReturn(false);
        when(passwordEncoder.encode(password)).thenReturn("encodedPassword");
        when(repository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        User result = service.register(name, email, password, role);

        // Assert
        assertNotNull(result);
        assertEquals(name, result.getName());
        assertEquals(email, result.getEmail());
        assertEquals("encodedPassword", result.getPassword()); // Should be encoded
        assertEquals(role, result.getRole());

        verify(repository).save(any(User.class));
    }

    @Test
    void register_ShouldThrowException_WhenEmailAlreadyExists() {
        // Arrange
        String email = "test@example.com";
        when(repository.existsByEmail(email)).thenReturn(true);

        // Act & Assert
        assertThrows(EntityExistsException.class, () -> service.register("Name", email, "pass", Role.USER));

        verify(repository, never()).save(any());
    }
}
