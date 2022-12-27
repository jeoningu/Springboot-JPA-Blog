package com.jig.blog.controller;

import com.jig.blog.config.auth.PrincipalDetail;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {
    @GetMapping({"", "/"})
    public String index(@AuthenticationPrincipal PrincipalDetail principalDetail) {

        // 로그인 확인
        if (principalDetail != null) {
            System.out.println("로그인 사용자 ID : " + principalDetail.getUsername());
        }

        // /WEB-INF/views/index.jsp
        return "index";
    }
}
