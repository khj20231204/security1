package com.cos.security1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {

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

    @GetMapping("/login")
    public @ResponseBody String login() {
        return "login";
    }

    //스프링시큐리티가 해당주소를 낚아챈다
    @GetMapping("/join")
    public @ResponseBody String join() {
        return "join";
    }

    @GetMapping("/joinProc")
    public @ResponseBody String joinProc() {
        return "회원가입 완료됨";
    }
}
