package com.jig.blog.test;

import com.jig.blog.model.Board;
import com.jig.blog.model.Reply;
import com.jig.blog.repository.BoardRepository;
import com.jig.blog.repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReplyControllerTest {

    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private ReplyRepository replyRepository;

    /**
     * 순환참조 문제 테스트
     *
     * 1. Board.java에서 replies @JsonIgnoreProperties({"board"})를 달아주지 않고
     * reply데이터가 있는 게시글 번호로 호출하면 무한 로딩 발생함.
     *
     * 2. @JsonIgnoreProperties({"board"}) 설정하고 호출하면 문제 x, 데이터 확인해보면 board-reply에 board데이터가 없음
     * @param id
     * @return
     */
    @GetMapping("/test/board/{id}")
    public Board testBoard(@PathVariable Long id) {
        return boardRepository.findById(id).get(); // jackson 라이브러리가 오브젝트를 josn으로 리턴하면서 모델의 getter를 호출한다.
    }

    /**
     * @JsonIgnoreProperties({"board"})을 Board.java에 설정했기 때문에, Reply를 바로 호출하는 경우에는 reply에 board 데이터가 포함된 채로 조회됨
     * @param id
     * @return
     */
    @GetMapping("/test/reply/{id}")
    public List<Reply> testReply(@PathVariable Long id) {
        return replyRepository.findAll();
    }
}
