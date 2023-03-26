package com.jig.blog.controller.api;

import com.jig.blog.config.security.PrincipalDetail;
import com.jig.blog.dto.ResponseDto;
import com.jig.blog.model.Board;
import com.jig.blog.model.Reply;
import com.jig.blog.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
public class BoardApiController {

    @Autowired
    private BoardService boardService;

    @PostMapping("/api/board")
    public ResponseDto<Integer> saveBoard(@RequestBody Board board, @AuthenticationPrincipal PrincipalDetail principalDetail) {
        boardService.saveBoard(board, principalDetail.getUser());

        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    @DeleteMapping("/api/board/{id}")
    public ResponseDto<Integer> deleteBoard(@PathVariable int id) {
        boardService.deleteBoard(id);

        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    @PutMapping("/api/board/{id}")
    public ResponseDto<Integer> updateBoard(@PathVariable int id, @RequestBody Board board) {

        boardService.updateBoard(id, board);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    @PostMapping("/api/board/{boardId}/reply")
    public ResponseDto<Integer> saveReply(@PathVariable int boardId, @RequestBody Reply reply, @AuthenticationPrincipal PrincipalDetail principalDetail) {
        boardService.saveReply(boardId, reply, principalDetail.getUser());

        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    @DeleteMapping("/api/board/{boardId}/reply/{replyId}")   // boardId는 사용 x, 주소 모양을 규칙적으로 만들기 위함.
    public ResponseDto<Integer> removeReply( @PathVariable int replyId) {
        boardService.removeReply(replyId);

        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    @PutMapping("/api/board/{boardId}/reply/{replyId}")   // boardId는 사용 x, 주소 모양을 규칙적으로 만들기 위함.
    public ResponseDto<Integer> modifyReply( @PathVariable int replyId, @RequestBody Reply reply) {
        System.out.println("BoardApiController.modifyReply");
        boardService.modifyReply(replyId, reply);

        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

}
