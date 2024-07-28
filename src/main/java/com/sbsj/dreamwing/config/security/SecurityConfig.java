package com.sbsj.dreamwing.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.authentication.AuthenticationManager;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    // PasswordEncoder는 BCryptPasswordEncoder를 사용 (패스워드 암호화)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        //  기본 설정 사용 안함
        http.httpBasic(httpBasic ->httpBasic.disable());

        // 사이트 위변조 요청 방지 (csrf 비활성화)
        http.csrf(csrf -> csrf.disable());


        http
            .authorizeRequests((authorizeRequests) -> authorizeRequests
            .requestMatchers(new AntPathRequestMatcher("/**")).permitAll());

        return http.build();
    }
}
