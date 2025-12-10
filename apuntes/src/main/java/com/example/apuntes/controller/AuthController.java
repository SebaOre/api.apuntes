package com.example.apuntes.controller;

import com.example.apuntes.config.JwtUtil;
import com.example.apuntes.model.User;
import com.example.apuntes.model.dto.RegisterRequest;
import com.example.apuntes.model.dto.LoginRequest;
import com.example.apuntes.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = {
        "https://sebaore13.dev",
        "https://www.sebaore13.dev",
        "https://api.sebaore13.dev",
        "http://localhost:5173",
        "http://localhost:8085"
})

public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;


    @PostMapping("/register")
    public User register(@RequestBody RegisterRequest req) {
        return userService.register(req);
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody LoginRequest req) {

        User user = userService.login(req);
        String token = jwtUtil.generateToken(user);

        return Map.of(
                "token", token,
                "user", user
        );
    }

    @GetMapping("/validate")
    public Map<String, Object> validateToken(@RequestHeader("Authorization") String header) {
        if (header == null || !header.startsWith("Bearer ")) {
            return Map.of(
                    "valid", false,
                    "reason", "No token provided"
            );
        }

        String token = header.substring(7);

        boolean valido = jwtUtil.isTokenValid(token);

        return Map.of(
                "valid", valido,
                "reason", valido ? "Token OK" : "Token expired or invalid"
        );
    }

}
