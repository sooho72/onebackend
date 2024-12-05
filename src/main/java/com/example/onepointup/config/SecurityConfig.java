package com.example.onepointup.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors().and() // CORS 허용
                .csrf().disable() // CSRF 보호 비활성화
                .authorizeHttpRequests()
                .requestMatchers("/api/**").permitAll() // /api/** 경로는 모두 허용
                .anyRequest().authenticated() // 그 외의 요청은 인증 필요
                .and()
                .httpBasic(); // HTTP Basic 인증 추가

        return http.build();
    }
}
