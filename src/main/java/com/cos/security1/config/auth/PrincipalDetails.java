package com.cos.security1.config.auth;

import com.cos.security1.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/*
시큐리티가 /login 주소 요청이 오면 낚아채서 로그인을 진행
로그인 진행이 완료가 되면 시큐리티 session을 만들어서 넣어준다.(Security ContextHolder 여기에 session 정보를 저장)
Security ContextHolder에 들어갈 수 있는 오브젝트는 정해져있다.
오브젝트 => Authentication 타입 객체
Authentication 안에 User 정보가 있어야 함
User 오브젝트 타입 => UserDetails 타입 객체

Security Session 안에 => Authenctication 안에 => UserDetails 안에 User 정보가 있다
이렇게 만들어놨다
*/
public class PrincipalDetails implements UserDetails {

    private User user;

    public PrincipalDetails(User user) {
        this.user = user;
    }

    //해당 User의 권한을 리턴하는 곳
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(user.getRole()));
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        //우리 사이트 1년 동안 회원이 로그인을 안하면 휴면 계정으로 하기로 함
        //현재시간 - 로긴시간 => 1년을 초과하면 return false;
        return true;
    }
}
