package com.kjh3.kjh3;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

// 이 클래스는 "Spring Security 설정 클래스"라는 것을 알려줌
@Configuration
public class SecurityConfig {

    // Spring Security의 필터 체인을 직접 정의
    // → 이 Bean이 있으면 기본 보안 설정을 우리가 덮어씀
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                // 🔹 CSRF 보안 비활성화
                // 기본적으로 Spring Security는 POST/PUT/DELETE 요청에
                // CSRF 토큰이 없으면 403 Forbidden을 발생시킴
                // 우리는 세션 기반 로그인 + 단순 게시판이므로 일단 비활성화
                .csrf(csrf -> csrf.disable())

                // 🔹 요청 URL에 대한 접근 권한 설정
                .authorizeHttpRequests(auth -> auth
                        // 모든 요청에 대해
                        // 인증 없이 접근 허용
                        // (/login, /board, /board/write 전부 허용됨)
                        .anyRequest().permitAll()
                );

        // 🔹 설정한 보안 필터 체인을 반환
        return http.build();
    }
}