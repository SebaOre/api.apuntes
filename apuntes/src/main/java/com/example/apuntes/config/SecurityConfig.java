package com.example.apuntes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtFilter jwtFilter) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth
                        // Login y registro libres
                        .requestMatchers("/auth/login", "/auth/register").permitAll()

                        // Swagger libre cuando estás en LOCAL
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**"
                        ).permitAll()

                        // El resto de la API requiere JWT
                        .anyRequest().authenticated()
                )

                // Filtro JWT para las rutas protegidas
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)

                // CORS activado
                .cors(cors -> {})

                // Desactiva formularios de Spring
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable());

        return http.build();
    }
}
