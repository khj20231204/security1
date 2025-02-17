package com.cos.security1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.security1.model.User;

//CRUD 함수를 JpaRepository가 들고 있음.
//@Repository라는 어노테이션이 없어도 IOC됨, JpaRepository를 상속했기 때문에..
//자동으로 bean으로 등록이 됨
public interface UserRepository extends JpaRepository<User, Integer> { //<User, Integer>에서 Integer는 User의 PK타입

    //findBy규칙 + 컬럼 : JPA Query Method
    //select * from user where username=? 이런 형식으로 직접 찾는다
    public User findByUsername(String username); 

    //select * from user where email=? 이런 형식으로 검색
    public User findByEmail(String email);
}
