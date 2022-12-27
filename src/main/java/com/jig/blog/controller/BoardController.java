package com.jig.blog.controller;

import com.jig.blog.config.auth.PrincipalDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {
    Logger log = LoggerFactory.getLogger(getClass());

    @GetMapping({"", "/"})
    public String index(@AuthenticationPrincipal PrincipalDetail principalDetail) {

        // 로그인 확인
        if (principalDetail != null) {
            log.info("로그인 사용자 ID : " + principalDetail.getUsername());
        }

        // /WEB-INF/views/index.jsp
        return "index";
    }

    @GetMapping("/board/saveForm")
    public String saveForm() {
        return "board/saveForm";
    }
}
