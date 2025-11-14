package com.example.product_management_API.domain.user;

import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User register(String name, String email, String rawPassword, Role role) {
        if (repository.existsByEmail(email)) {
            throw new EntityExistsException("Email já cadastrado: " + email);
        }
        User user = User.builder()
                .name(name)
                .email(email)
                .password(passwordEncoder.encode(rawPassword))
                .role(role == null ? Role.USER : role)
                .build();
        return repository.save(user);
    }
}

