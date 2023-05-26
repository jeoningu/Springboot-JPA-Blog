package com.jig.blog.board;

import com.jig.blog.dto.BoardReqDto;
import com.jig.blog.dto.ReplyReqDto;
import com.jig.blog.model.*;
import com.jig.blog.repository.BoardRepository;
import com.jig.blog.repository.ReplyRepository;
import com.jig.blog.service.BoardService;
import com.jig.blog.service.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@Transactional
class BoardServiceTest {
    @Autowired
    private ReplyRepository replyRepository;
    @Autowired
    private BoardService boardService;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private UserService userService;

    @Test
    @Order(1)
    @DisplayName("게시글 저장 테스트")
    @Transactional
    void saveLike() {
        // given
        User user = User.builder()
                .username("user1")
                .password("1234")
                .name("user1")
                .email("user1@gmail.com")
                .role(RoleType.USER)
                .build();
        userService.joinUser(user);

        BoardReqDto boardReqDto = BoardReqDto.builder()
                .title("title")
                .content("content")
                .build();

        // when
        boardService.saveBoard(boardReqDto, user);

        // then
        Board savedBoard = boardRepository.findAllByOrderByCreatedDateDesc().get(0);
        assertThat(savedBoard.getTitle()).isEqualTo("title");
        assertThat(savedBoard.getContent()).isEqualTo("content");
        assertThat(savedBoard.getUser().getId()).isEqualTo(user.getId());
    }

    @Test
    @Order(2)
    @DisplayName("게시글 수정 테스트")
    @Transactional
    void modifyBoard() {
        // given
        User user = User.builder()
                .username("user1")
                .password("1234")
                .name("user1")
                .email("user1@gmail.com")
                .role(RoleType.USER)
                .build();
        userService.joinUser(user);

        BoardReqDto saveBoardReqDto = BoardReqDto.builder()
                .title("title")
                .content("content")
                .build();
        boardService.saveBoard(saveBoardReqDto, user);

        Board savedBoard = boardRepository.findAllByOrderByCreatedDateDesc().get(0);

        BoardReqDto updateBoardReqDto = BoardReqDto.builder()
                .title("updated title")
                .content("updated content")
                .build();

        // when
        boardService.modifyBoard(savedBoard.getId(), updateBoardReqDto);

        // then
        Board updatedBoard = boardRepository.findById(savedBoard.getId()).orElse(null);
        assertThat(updatedBoard).isNotNull();
        assertThat(updatedBoard.getTitle()).isEqualTo("updated title");
        assertThat(updatedBoard.getContent()).isEqualTo("updated content");
    }

    @Test
    @Order(3)
    @DisplayName("게시글 삭제 테스트")
    @Transactional
    void deleteBoard() {
        // given
        User user = User.builder()
                .username("user1")
                .password("1234")
                .name("user1")
                .email("user1@gmail.com")
                .role(RoleType.USER)
                .build();
        userService.joinUser(user);

        BoardReqDto saveBoardReqDto = BoardReqDto.builder()
                .title("title")
                .content("content")
                .build();
        boardService.saveBoard(saveBoardReqDto, user);

        Board savedBoard = boardRepository.findAllByOrderByCreatedDateDesc().get(0);

        // when
        boardService.removeBoard(savedBoard.getId());

        // then
        Optional<Board> deletedBoard = boardRepository.findById(savedBoard.getId());
        assertThat(deletedBoard).isEmpty();
    }

    @Test
    @Order(4)
    @DisplayName("게시글 댓글 추가 테스트")
    @Transactional
    void saveReply() {
        // given
        User user = User.builder()
                .username("user1")
                .password("1234")
                .name("user1")
                .email("user1@gmail.com")
                .role(RoleType.USER)
                .build();
        userService.joinUser(user);

        BoardReqDto saveBoardReqDto = BoardReqDto.builder()
                .title("title")
                .content("content")
                .build();
        boardService.saveBoard(saveBoardReqDto, user);

        Board savedBoard = boardRepository.findAllByOrderByCreatedDateDesc().get(0);

        ReplyReqDto replyReqDto = ReplyReqDto.builder()
                .content("content")
                .build();

        // when
        boardService.saveReply(savedBoard.getId(), replyReqDto, user);

        // then
        Reply savedReply = replyRepository.findAllByOrderByCreatedDateDesc().get(0);
        assertThat(savedReply.getContent()).isEqualTo("content");
        assertThat(savedReply.getUser().getId()).isEqualTo(user.getId());
        assertThat(savedReply.getBoard().getId()).isEqualTo(savedBoard.getId());
    }

    @Test
    @Order(5)
    @DisplayName("게시글 댓글 수정 테스트")
    @Transactional
    void updateReply() {
        // given
        User user = User.builder()
                .username("user1")
                .password("1234")
                .name("user1")
                .email("user1@gmail.com")
                .role(RoleType.USER)
                .build();
        userService.joinUser(user);

        BoardReqDto saveBoardReqDto = BoardReqDto.builder()
                .title("title")
                .content("content")
                .build();
        boardService.saveBoard(saveBoardReqDto, user);

        Board savedBoard = boardRepository.findAllByOrderByCreatedDateDesc().get(0);

        ReplyReqDto replyReqDto = ReplyReqDto.builder()
                .content("content")
                .build();

        boardService.saveReply(savedBoard.getId(), replyReqDto, user);

        Reply savedReply = replyRepository.findAllByOrderByCreatedDateDesc().get(0);

        ReplyReqDto updateReplyReqDto = ReplyReqDto.builder()
                .content("updated content")
                .build();

        // when
        boardService.modifyReply(savedReply.getId(), updateReplyReqDto);

        // then
        Reply updatedReply = replyRepository.findById(savedReply.getId()).orElse(null);
        assertThat(updatedReply).isNotNull();
        assertThat(updatedReply.getContent()).isEqualTo("updated content");
    }

    @Test
    @Order(6)
    @DisplayName("게시글 댓글 삭제 테스트")
    @Transactional
    void deleteReply() {
        // given
        User user = User.builder()
                .username("user1")
                .password("1234")
                .name("user1")
                .email("user1@gmail.com")
                .role(RoleType.USER)
                .build();
        userService.joinUser(user);

        BoardReqDto saveBoardReqDto = BoardReqDto.builder()
                .title("title")
                .content("content")
                .build();
        boardService.saveBoard(saveBoardReqDto, user);

        Board savedBoard = boardRepository.findAllByOrderByCreatedDateDesc().get(0);

        ReplyReqDto replyReqDto = ReplyReqDto.builder()
                .content("content")
                .build();

        boardService.saveReply(savedBoard.getId(), replyReqDto, user);

        Reply savedReply = replyRepository.findAllByOrderByCreatedDateDesc().get(0);

        // when
        boardService.removeReply(savedReply.getId());

        // then
        Optional<Reply> deletedReply = replyRepository.findById(savedReply.getId());
        assertThat(deletedReply).isEmpty();
    }

}