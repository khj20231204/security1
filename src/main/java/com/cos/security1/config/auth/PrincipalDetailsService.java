package com.cos.security1.config.auth;

import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authorization.method.AuthorizeReturnObject;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.management.loading.PrivateClassLoader;
import java.security.Principal;

//시큐리티 설정에서 loginProcessingUrl("/login");
//login 요청이 오면 자동으로 UserDetailsService 타입으로 IoC되어있는 loadUserByUsername 함수가 실행
//그냥 이렇게 만들었다
@Slf4j
@Service
public class PrincipalDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    /*
    매개변수 String username은 loginForm.html의
    <input type="text" name="username" placeholder="Username"/>
    여기 name의 username을 말한다. 만약 여기 name을 username2로 변경한다면
    <input type="text" name="username2" placeholder="Username"/>
    SecurityConfig에
    .formLogin(login -> login
            .usernameParameter("username2")
    );
    로 username 파라미터를 변경해 줘야한다.
    */

    /* 로그인 버튼을 클릭하면 IoC에서 UserDetailsService를 찾는다.
    이후 UserDetails를 찾고 username파라미터를 가져온다 */

        User userEntity = userRepository.findByUsername(username);

        if(userEntity != null) {
            return new PrincipalDetails(userEntity);
            //return이 되면 UserDetails가 Authentication에 들어가고, 다시 Security Session에 들어간다
            // Seucrity Session(내부 Authentication(내부 UserDetails))
        }
        return null;
    }
}
