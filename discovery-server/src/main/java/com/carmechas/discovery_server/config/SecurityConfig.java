package com.carmechas.discovery_server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // OBLIGATORIO para que los micros se registren
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/").permitAll()         // Permite ver la UI de Eureka
                        .requestMatchers("/eureka/**").permitAll() // Permite registro de servicios
                        .requestMatchers("/actuator/**").permitAll()
                        .anyRequest().authenticated()
                );
        return http.build();
    }
}