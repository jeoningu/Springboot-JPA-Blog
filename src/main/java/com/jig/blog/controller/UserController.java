package com.jig.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * 접속 허용 경로
 * - 인증 안된 사용자들이 출입할 수 잇는 경로로서 /auth/** 허용
 * - 기본 경로 ( / ) 면 index.jsp 허용
 * - static 이하에 있는 /js/**. /css/**, /image/** 허용
 *
 */
@Controller
public class UserController {

    @GetMapping("/auth/joinForm")
    public String joinForm() {
        return "user/joinForm";
    }

    @GetMapping("/auth/loginForm")
    public String loginForm() {
        return "user/loginForm";
    }

    @GetMapping("/user/updateForm")
    public String updateForm() {
        return "user/updateForm";
    }
}
