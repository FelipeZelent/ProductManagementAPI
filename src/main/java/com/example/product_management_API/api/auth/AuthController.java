package com.example.product_management_API.api.auth;

import com.example.product_management_API.api.auth.dto.AuthResponse;
import com.example.product_management_API.api.auth.dto.LoginRequest;
import com.example.product_management_API.api.auth.dto.RegisterRequest;
import com.example.product_management_API.domain.user.Role;
import com.example.product_management_API.domain.user.User;
import com.example.product_management_API.domain.user.UserService;
import com.example.product_management_API.infra.security.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthResponse register(@RequestBody @Valid RegisterRequest req) {
        Role role = req.role() == null ? Role.USER : req.role();
        User user = userService.register(req.name(), req.email(), req.password(), role);
        String token = jwtService.generateToken(user);
        return new AuthResponse(token);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody @Valid LoginRequest req) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.email(), req.password())
        );
        String token = jwtService.generateToken((org.springframework.security.core.userdetails.UserDetails) auth.getPrincipal());
        return new AuthResponse(token);
    }
}

