package com.example.apuntes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {

        CorsConfiguration config = new CorsConfiguration();

        // 🔥 Dominios permitidos
        config.setAllowedOrigins(List.of(
                "https://sebaore13.dev",
                "https://www.sebaore13.dev",
                "https://api.sebaore13.dev",
                "http://localhost:5173",
                "http://localhost:8085"
        ));

        config.addAllowedHeader("*");     // permite cualquier header
        config.addAllowedMethod("*");     // GET, POST, PUT, DELETE, etc
        config.setAllowCredentials(false); // no usas cookies, así que OFF

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}
