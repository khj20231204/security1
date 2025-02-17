package com.cos.security1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;

@Controller
public class IndexController {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    //localhost:8080
    @GetMapping({"","/"})
    public String index() {
        //mustache 기본폴더 src/main/resources/
        //뷰리졸버 설정 : templates (prefix), .mustache (suffix) 생략가능
        return "index"; // src/main/resources/templates/index.mustache를 원래 기본으로 찾게된다.
    }

    @GetMapping("/user")
    public @ResponseBody String user() {
        return "user";
    }

    @GetMapping("/admin")
    public @ResponseBody String admin() {
        return "admin";
    }

    @GetMapping("/manager")
    public @ResponseBody String manager() {
        return "manager";
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        return "loginForm";
    }

    @GetMapping("/joinForm")
    public String joinForm() {
        return "joinForm";
    }

    //스프링시큐리티가 해당주소를 낚아챈다
    @PostMapping("/join")
    public String join(User user) {

        System.out.println(user);

        //id와 createDate는 자동으로 생성됨
        user.setRole("ROLE_USER");
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
         /*
        스프링 시큐리티는 암호화된 비밀번호만 허용
        📌 비밀번호 인증 과정
        사용자가 로그인 요청 (username, password 전송)
        Security는 UserDetailsService에서 해당 사용자 정보를 조회
        데이터베이스에서 가져온 암호화된 비밀번호와,
        사용자가 입력한 비밀번호를 PasswordEncoder를 사용해 비교
        비밀번호가 암호화되지 않았다면 인증 실패! (로그인 불가)
        */

        userRepository.save(user); //회원가입은 잘됨. 하지만 비밀번호가 1234가 됨
        /*
        userRepository.save(user);는 Spring Data JPA에서 제공하는 메서드로,
        User 엔티티를 데이터베이스에 저장하거나 업데이트하는 역할을 합니다.

        📌 save()는 다음 두 가지 동작을 수행합니다:
        새로운 엔티티(user)이면 → INSERT (새 데이터 저장)
        이미 존재하는 엔티티(id가 존재)면 → UPDATE (기존 데이터 수정)
        */

        return "redirect:/loginForm";
        //redirect는 위에 있는 loginForm이라는 함수를 호출
    }

    @Secured("ROLE_ADMIN") //권한 1개사용
    @GetMapping("/info")
    public @ResponseBody String info() {
        return "개인정보";
    }

    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')") //권한 2개 사용
    @GetMapping("/data")
    public @ResponseBody String data() {
        return "data 페이지";
    }
}
