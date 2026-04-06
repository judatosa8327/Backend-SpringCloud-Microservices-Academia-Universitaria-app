package com.backend.service.course.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers(HttpMethod.GET, "/api/v1/cursos/**").hasAuthority("SCOPE_api.read")
                        .requestMatchers(HttpMethod.POST, "/api/v1/cursos/**").hasAuthority("SCOPE_api.write")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/cursos/**").hasAuthority("SCOPE_api.write")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/cursos/**").hasAuthority("SCOPE_api.write")
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
                .csrf(csrf -> csrf.disable())
                .sessionManagement(m -> m.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }
}
