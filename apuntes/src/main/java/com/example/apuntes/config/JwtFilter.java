package com.example.apuntes.config;

import com.example.apuntes.model.User;
import com.example.apuntes.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public JwtFilter(JwtUtil jwtUtil, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        // Si no viene token, seguir normal (rutas públicas)
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String token = authHeader.substring(7);

        // Validar token
        if (!jwtUtil.isTokenValid(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Obtener email
        String email = jwtUtil.getEmailFromToken(token);
        if (email == null) {
            filterChain.doFilter(request, response);
            return;
        }

        // Obtener rol
        String role = jwtUtil.getRoleFromToken(token);
        if (role == null) {
            filterChain.doFilter(request, response);
            return;
        }

        // Obtener usuario desde Mongo (por si necesitamos más datos)
        User user = userRepository.findByEmail(email).orElse(null);

        // Crear autoridad Spring Security
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);

        // Crear objeto de autenticación
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(
                        user,            // principal (objeto user completo)
                        null,            // credenciales (no se usa)
                        List.of(authority)
                );

        // Guardar en el contexto de seguridad de Spring
        SecurityContextHolder.getContext().setAuthentication(authToken);

        // Continuar con la cadena de filtros
        filterChain.doFilter(request, response);
    }
}
