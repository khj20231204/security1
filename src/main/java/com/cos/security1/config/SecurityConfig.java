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
            .anyRequest().permitAll() //그 외 모든 요청은 허용
            )
            .formLogin(login -> login
                    .loginPage("/login") // 로그인 페이지 설정
                    .defaultSuccessUrl("/home", true) // 로그인 성공 시 이동할 페이지
                    .permitAll() // 로그인 페이지는 누구나 접근 가능
            );

        //.anyRequest().authenticated()); 모든 요청에 대해 인증(로그인) 필요
        // 로그인하지 않은 사용자가 인증이 필요한 페이지에 접근하면 자동으로 로그인 페이지로 이동
        //✔ 권한(ROLE)이 부족한 사용자는 기본적으로 403 Forbidden (로그인 페이지 이동 X)
        //✔ 권한이 부족한 경우 로그인 페이지로 리디렉트하려면 accessDeniedHandler() 설정 필요

        return http.build();
    }
}
