package com.cos.security1.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;

/*
시큐리티가 만든 세션에 데이터를 저장하는 과정이고 login이 이루어졌을 때 실행이 된다.
Security Session -> Authenctication -> UserDetails(PrincipalDetails)
Session에 데이터를 넣기 위해서는 Authentication 객체가 있어야하고 
PrincipalDetailsService에서 이 작업을 하게 된다.

UserDetails는 PrincipalDetails가 역할한다.
UserDetails를 받아서 Authentication 객체를 만들어야하는데 
PrincipalDetailsService가 Authentication 객체의 역할을 한다.

public UserDetails loadUserByUsername(String username)
loadUserByUsername 함수에서 PrincipalDetails(UserDetails를 상속받았기 때문)를 리턴하면
Authentication 내부의 UserDetails에 userEntity가 입력된다.

Authentication을 리턴하면 Session 내부의 Authentication에 입력된다.
Session(내부 Authentication(내부 UserDetails(UserDetails 리턴)))
=> UserDetails를 리턴하면 Authentication 내부의 UsserDeails에 저장 
Authenctication을 리턴하면 Session의 내부 Authenctication에 저장
*/

//시큐리티 설정에서 loginProcessingUrl("/login")
//login 요청이 오면 자동으로 UserDetailsService 타입으로 IoC되어 있는 loadUserByUsername 함수가 실행된다.
@Service
public class PrincipalDetailsService implements UserDetailsService {

   @Autowired
   private UserRepository userRepository;

   /* 
   login으로 action을 날리는 loginForm.html에 있는
   <form action="/login" method="post">
      <input type="text" name="username" placeholder="Username" /><br/>
      <input type="password" name="password" placeholder="Password" /><br/>
      <button>로그인</button>
   </form> 
   의 name에 있는 username과 밑에 매개변수 loadUserByUsername(String username)의 username변수명이 같아야 한다

   <input type="text" name="username2" placeholder="Username" /><br/>
   로 한다거나
   loadUserByUsername(String username222)로 하게 되면 매핑되지 않는다

   만약 <input type="text" name="username2" placeholder="Username" /><br/>로
   username을 변경하고 싶다면 
   SecurityConfig.java에 설정되어 있는
   .formLogin(login -> login
                  .loginPage("/loginForm") 
                  .loginProcessingUrl("/login")
                  .usernameParameter("username2") //usernameParameter에서 username2추가
                  .defaultSuccessUrl("/")
                  .permitAll() 
         );
   .usernameParameter("username2")를 추가해 줘야 한다
   */

   @Override
   public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      
      User userEntity = userRepository.findByUsername(username);

      if(userEntity != null){
         return new PrincipalDetails(userEntity);
         //이 값을 리턴하면 Authentication 내부의 UserDetails에 userEntity가 입력된다.
      }
         
      return null;
   }
   
}