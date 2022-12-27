package com.jig.blog.controller;

import com.jig.blog.config.auth.PrincipalDetail;
import com.jig.blog.service.BoardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/*
@Controller는 메서드에서 리턴시에 viewResolver가 view까지 model을 가져간다.
 */
@Controller
public class BoardController {
    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private BoardService boardService;

    @GetMapping({"", "/"})
    public String index(Model model) {
        model.addAttribute("boards", boardService.getList());

        // /WEB-INF/views/index.jsp
        return "index";
    }

    @GetMapping("/board/saveForm")
    public String saveForm() {
        return "board/saveForm";
    }
}
