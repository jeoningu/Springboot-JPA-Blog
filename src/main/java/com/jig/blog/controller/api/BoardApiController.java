package com.jig.blog.controller.api;

import com.jig.blog.config.security.PrincipalDetail;
import com.jig.blog.dto.BoardReqDto;
import com.jig.blog.dto.ReplyReqDto;
import com.jig.blog.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
public class BoardApiController {

    @Autowired
    private BoardService boardService;

    @PostMapping("/api/board")
    public ResponseEntity<String> saveBoard(@RequestBody BoardReqDto boardReqDto, @AuthenticationPrincipal PrincipalDetail principalDetail) {
        boardService.saveBoard(boardReqDto, principalDetail.getUser());

        return ResponseEntity.status(HttpStatus.OK).body("");
    }

    @DeleteMapping("/api/board/{id}")
    public ResponseEntity<String> removeBoard(@PathVariable Long id) {
        boardService.removeBoard(id);

        return ResponseEntity.status(HttpStatus.OK).body("");
    }

    @PutMapping("/api/board/{id}")
    public ResponseEntity<String> modifyBoard(@PathVariable Long id, @RequestBody BoardReqDto boardReqDto) {

        boardService.modifyBoard(id, boardReqDto);
        return ResponseEntity.status(HttpStatus.OK).body("");
    }

    @PostMapping("/api/board/{boardId}/reply")
    public ResponseEntity<String> saveReply(@PathVariable Long boardId, @RequestBody ReplyReqDto replyReqDto, @AuthenticationPrincipal PrincipalDetail principalDetail) {
        boardService.saveReply(boardId, replyReqDto, principalDetail.getUser());

        return ResponseEntity.status(HttpStatus.OK).body("");
    }

    @DeleteMapping("/api/board/{boardId}/reply/{replyId}")   // boardId는 사용 x, 주소 모양을 규칙적으로 만들기 위함.
    public ResponseEntity<String> removeReply( @PathVariable Long replyId) {
        boardService.removeReply(replyId);

        return ResponseEntity.status(HttpStatus.OK).body("");
    }

    @PutMapping("/api/board/{boardId}/reply/{replyId}")   // boardId는 사용 x, 주소 모양을 규칙적으로 만들기 위함.
    public ResponseEntity<String> modifyReply( @PathVariable Long replyId, @RequestBody ReplyReqDto replyReqDto) {
        System.out.println("BoardApiController.modifyReply");
        boardService.modifyReply(replyId, replyReqDto);

        return ResponseEntity.status(HttpStatus.OK).body("");
    }

}
