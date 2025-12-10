package com.example.apuntes.config;

import com.example.apuntes.model.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    // Clave secreta para firmar el token (cámbiala en producción)
    private final String SECRET = "MI_SUPER_SECRETO_PULENTO_COMPAAA_2025";

    // Duración del token: 8 horas
    private final long EXPIRATION_TIME = 1000 * 60 * 60 * 8;



    // Genera un token JWT para un usuario

    public String generateToken(User user) {

        // Spring Security usa ROLE_ADMIN / ROLE_USER
        String roleFormatted = user.getRole().startsWith("ROLE_")
                ? user.getRole()
                : "ROLE_" + user.getRole();

        return JWT.create()
                .withSubject(user.getEmail())    // Identificador principal del usuario
                .withClaim("id", user.getId())
                .withClaim("name", user.getName())
                .withClaim("role", roleFormatted) // IMPORTANTE
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC256(SECRET));
    }


     // Obtiene el email (subject) desde un token válido

    public String getEmailFromToken(String token) {
        try {
            return JWT.require(Algorithm.HMAC256(SECRET))
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
            return null;
        }
    }


     // Obtiene el rol desde el token
    public String getRoleFromToken(String token) {
        try {
            return JWT.require(Algorithm.HMAC256(SECRET))
                    .build()
                    .verify(token)
                    .getClaim("role")
                    .asString();
        } catch (Exception e) {
            return null;
        }
    }


     //Valida si el token es correcto y no expiró

    public boolean isTokenValid(String token) {
        try {
            JWT.require(Algorithm.HMAC256(SECRET))
                    .build()
                    .verify(token);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    public String getUserIdFromToken(String token) {
        try {
            return JWT.require(Algorithm.HMAC256(SECRET))
                    .build()
                    .verify(token)
                    .getClaim("id")
                    .asString();
        } catch (Exception e) {
            return null;
        }
    }

    public String extractToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }

        return null;
    }


}
