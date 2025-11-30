package com.example.apuntes.service;

import com.example.apuntes.model.User;
import com.example.apuntes.model.dto.RegisterRequest;
import com.example.apuntes.model.dto.LoginRequest;
import com.example.apuntes.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;


    public User register(RegisterRequest req) {

        if (repository.existsByEmail(req.getEmail())) {
            throw new RuntimeException("Correo ya registrado");
        }

        User user = new User();
        user.setName(req.getName());
        user.setEmail(req.getEmail());
        user.setPassword(passwordEncoder.encode(req.getPassword()));

        return repository.save(user);
    }

    public User login(LoginRequest req) {

        User user = repository.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("Correo o contraseña incorrectos"));

        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new RuntimeException("Correo o contraseña incorrectos");
        }

        return user;
    }
}
