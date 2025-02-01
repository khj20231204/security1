package com.cos.security1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration //자바가 인식하는 설정 클래스로 지정
@EnableWebSecurity //스프링 시큐리티 필터가 스프링 필터체인에 등록이 됨
public class SecurityConfig{

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((authorizeRequests) -> authorizeRequests
            .requestMatchers("/user/**").authenticated() //user는 인증된 사용자만 접근
            .requestMatchers("/manager").hasAnyRole("ADMIN", "MANAGER") //ADMIN또는 MANAGER가 접근
            .requestMatchers("/admin").hasRole("ADMIN") //ADMIN만 접근
            .anyRequest().permitAll()); //그 외 모든 요청은 허용

        return http.build();
    }
}
