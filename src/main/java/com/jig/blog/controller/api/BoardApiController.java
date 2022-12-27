package com.jig.blog.controller.api;

import com.jig.blog.config.auth.PrincipalDetail;
import com.jig.blog.dto.ResponseDto;
import com.jig.blog.model.Board;
import com.jig.blog.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BoardApiController {

    @Autowired
    private BoardService boardService;

    @PostMapping("/api/board")
    public ResponseDto<Integer> save(@RequestBody Board board, @AuthenticationPrincipal PrincipalDetail principalDetail) {
        boardService.save(board, principalDetail.getUser());

        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

}
