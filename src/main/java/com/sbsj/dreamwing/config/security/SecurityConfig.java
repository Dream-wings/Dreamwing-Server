package com.sbsj.dreamwing.config.security;

import com.sbsj.dreamwing.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //  기본 설정 사용 안함
        http.httpBasic(httpBasic ->httpBasic.disable());

        // 사이트 위변조 요청 방지 (csrf 비활성화)
        http.csrf(csrf -> csrf.disable());


        http
                .authorizeRequests((authorizeRequests) -> authorizeRequests
                        .requestMatchers(new AntPathRequestMatcher("/**")).permitAll());
//        http
//                .httpBasic(httpBasic -> httpBasic.disable()) // 기본 인증 비활성화
//                .csrf(csrf -> csrf.disable()) // CSRF 보호 비활성화
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 관리 없음
//                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("/user/withdraw").hasRole("USER") // /user/withdraw 경로는 USER 역할을 가진 사용자만 접근 가능
//                        .requestMatchers("/user/login", "/user/join").permitAll() // /login 및 /join 경로는 인증 없이 접근 가능
//                        .anyRequest().authenticated() // 나머지 모든 경로는 인증 필요
//                )
//                .exceptionHandling(exceptions -> exceptions
//                        .accessDeniedHandler(customAccessDeniedHandler)
//                        .authenticationEntryPoint(customAuthenticationEntryPoint)
//                )
//                .addFilterBefore(new JwtFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class); // JWT 필터 추가

        return http.build();
    }
}
