package com.cos.security1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration //자바가 인식하는 설정 클래스로 지정
@EnableWebSecurity //스프링 시큐리티 필터가 스프링 필터체인에 등록이 됨
@EnableMethodSecurity(securedEnabled = true) //Role기반 보안적용
public class SecurityConfig{

    //해당 메서드의 리턴되는 오브젝트를 IoC로 등록해준다.
    @Bean
    public BCryptPasswordEncoder encoderPwd(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests((authorizeRequests) -> authorizeRequests
            .requestMatchers("/user/**").authenticated() //user는 로그인하면 접근 가능, 인증되면이란? => 로그인
            .requestMatchers("/manager").hasAnyRole("ADMIN", "MANAGER") //ADMIN또는 MANAGER가 접근
            .requestMatchers("/admin").hasRole("ADMIN") //ADMIN만 접근
            /*
            ➡️ .hasAnyRole("ADMIN", "MANAGER")과 hasRole("ADMIN")은 내부적으로 "ROLE_"를 자동으로 붙임
            */
            .anyRequest().permitAll() //그 외 모든 요청은 허용
            )
            .formLogin(login -> login
                    .loginPage("/loginForm") // 로그인 페이지 설정
                    .loginProcessingUrl("/login") //login 주소가 호출이 되면 시큐리티가 낚아채서 대신 로그인을 진행해줌
                    //그렇기 때문에 cotroller에 더 이상 login페이지가 없어도 된다.
                    .defaultSuccessUrl("/") //로그인이 성공하면 main페이지로 간다
                    .permitAll() // 로그인 페이지는 누구나 접근 가능
            );


        //.anyRequest().authenticated()); 모든 요청에 대해 인증(로그인) 필요

        /*
        formLogin 으로 가는 경우
        ✔ 로그인하지 않은 사용자가 인증이 필요한 페이지에 접근하면 자동으로 로그인 페이지로 이동
        ✔ 권한(ROLE)이 부족한 사용자는 기본적으로 403 Forbidden (로그인 페이지 이동 X)
        ✔ 권한이 부족한 경우 로그인 페이지로 리디렉트하려면 accessDeniedHandler() 설정 필요
        */


        return http.build();
    }
}
