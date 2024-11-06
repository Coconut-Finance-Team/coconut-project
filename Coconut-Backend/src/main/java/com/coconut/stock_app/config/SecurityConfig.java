package com.coconut.stock_app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("*").permitAll() // 인증 없이 접근 허용
                                .anyRequest().authenticated()
                )
                .csrf().disable(); // CSRF 비활성화 (필요한 경우)

        return http.build();
    }
}