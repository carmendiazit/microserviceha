package com.carmechas.api_gateway.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity // <--- Nota el "Flux" (reactivo)
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchange -> exchange
                        // 1. Permitir Eureka
                        .pathMatchers("/eureka/**").permitAll()
                        // 2. Permitir los Actuators de TODOS los microservicios
                        // Esto permite que /order-service/actuator/** sea libre en el Gateway
                        .pathMatchers("/*/actuator/**").permitAll()
                        // 3. Todo lo demás requiere autenticación
                        .anyExchange().authenticated()
                )
                .oauth2Login(org.springframework.security.config.Customizer.withDefaults())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(org.springframework.security.config.Customizer.withDefaults()))
                .build();
    }
}

