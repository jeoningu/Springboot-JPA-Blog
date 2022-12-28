package com.jig.blog.controller;

import com.jig.blog.service.BoardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/*
@Controller는 메서드에서 리턴시에 viewResolver가 view까지 model을 가져간다.
 */
@Controller
public class BoardController {
    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private BoardService boardService;

    @GetMapping({"", "/"})
    public String index(Model model, @PageableDefault(size=3, sort="id", direction=Sort.Direction.DESC)Pageable pageable) {
        model.addAttribute("boards", boardService.getBoardList(pageable));

        // /WEB-INF/views/index.jsp
        return "index";
    }

    @GetMapping("/board/saveForm")
    public String saveForm() {
        return "board/saveForm";
    }

    @GetMapping("/board/{id}")
    public String getBoardDetail(@PathVariable int id, Model model) {
        model.addAttribute("board", boardService.getBoardDetail(id));
        return "board/detail";
    }
}
