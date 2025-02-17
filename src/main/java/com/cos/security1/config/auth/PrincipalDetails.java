package com.cos.security1.config.auth;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cos.security1.model.User;

/*
클라이언트가 로그인을 해서 요청이 오면 SecurityConfig에서 /login을 주소를 낚아채서 진행을 시키는데
로그인 진행이 완료되면 시큐리티가 자신의 session을 만들어서 넣어준다. 
세션 이름은 Security ContextHolder 이다
Security ContextHolder에 들어갈 수 있는 세션이 정해져있는데 그건 Authentication 타입 객체이다
Authentication 안에 User 정보가 있어야 된다.   
User 오브젝트 타입 => UserDetails 타입 객체

Security Session -> Authentication -> UserDetails(PrincipalDetails)
*/
public class PrincipalDetails implements UserDetails{

   private User user;

   public PrincipalDetails(User user){
      this.user = user;
   }

   //해당 User의 권한을 리턴하는 곳
   //밑에는 예전 버전
   /*
   @Override
   public Collection<? extends GrantedAuthority> getAuthorities() {
      
      //user.getRole(); 의 리턴타입은 String이기 때문에 바로 리턴을 할 수 없다
      
      Collection<GrantedAuthority> collect = new ArrayList<>();
      collect.add(new GrantedAuthority() {

         @Override
         public String getAuthority() {
            return user.getRole();
         }
         
      });
      return collect;
   }
   */

   //SimpleGrantedAuthority을 활용한 최신 버전의 getAuthorities
   @Override
   public Collection<? extends GrantedAuthority> getAuthorities() {
      return List.of(new SimpleGrantedAuthority(user.getRole()));
   }

   @Override
   public String getPassword() {
      return user.getPassword();
   }

   @Override
   public String getUsername() {
      return user.getUsername();
   }
}
