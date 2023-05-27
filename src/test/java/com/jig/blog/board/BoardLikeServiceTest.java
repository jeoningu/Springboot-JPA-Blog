package com.jig.blog.board;


import com.jig.blog.model.Board;
import com.jig.blog.model.Like;
import com.jig.blog.model.RoleType;
import com.jig.blog.model.User;
import com.jig.blog.repository.BoardRepository;
import com.jig.blog.repository.LikeRepository;
import com.jig.blog.repository.ReplyRepository;
import com.jig.blog.repository.UserRepository;
import com.jig.blog.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 실제 db에 데이터 추가하면서 테스트를 수행
 *  - db에 데이터 없는 초기 상태라고 가정하고 테스트
 *  - 운영 중에도 테스트 수행할 수 있게 변경 필요할 듯
 *   : 테스트코드를 수정해서 실제 db에 테스트 했다가 테스트한 데이터는 지우는 방향으로 해도 되겠지만 테스트용 db를 사용하는 방법이 안전할 듯.
 */
@SpringBootTest
@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // @BeforeAll,@Afterall 을 사용하기 위함
public class BoardLikeServiceTest {

    @Autowired
    private BoardService boardService;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private ReplyRepository replyRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private List<User> userList;
    private List<Board> boardList;
    private int DO_LIKE_SU = 100;

    /*
    beforeEach : 좋아요 요청할 게시글 추가
    afterEach : 매 테스트마다 좋아요 카운트를 하기 위해 추가했던 게시글, 좋아요 정보 삭제해서 초기 상태로 만든다.
    beforeAll : 동시성 테스트 요청시 사용될 사용자들 추가
    afterAll : 사용자 삭제
     */
    @BeforeEach
    public void beforeEach() {
        boardList = new ArrayList<Board>();
        for (int i=0; i<2; i++) {
            Board board = Board.builder()
                    .title("title")
                    .content("content")
                    .user(userList.get(0))
                    .build();

            this.boardList.add(board);
            boardRepository.saveAndFlush(board);
        }
    }

    @AfterEach
    public void afterEach() {
        likeRepository.deleteAll();
        boardRepository.deleteAll();
    }

    @BeforeAll // 테스트 클래스에서 한번만 실행
    public void beforeAll() {
        userList = new ArrayList<User>();
        for (int i=0; i<DO_LIKE_SU; i++) {
            User user = User.builder()
                    .username("test" + i)
                    .password(bCryptPasswordEncoder.encode("1234"))
                    .name("test1")
                    .email("test1@gmail.com")
                    .role(RoleType.USER)
                    .build();
            this.userList.add(user);
            userRepository.saveAndFlush(user);
        }
    }

    @AfterAll
    public void afterAll() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("좋아요 요청 기본 테스트")
    void like() {
        // given
        // beforeEach()에서 사용자, 게시글 추가

        // when
        Long boardId = boardList.get(0).getId();
        User user = userList.get(0);
        boardService.likeUseRedisson(boardId, user);

        // then
        Board board = boardRepository.findWithLikesById(boardId).orElseGet(null);

        // 좋아요 수 확인
        assertThat(board.getLikeCount()).isEqualTo(1);

        // 좋아요 정보 확인
        Like like = board.getLikes().get(0);
        assertThat(like.getUser().getId()).isEqualTo(user.getId());
        assertThat(like.getBoard().getId()).isEqualTo(boardId);
    }

    @DisplayName("2개 게시글에 좋아요 동시 요청 테스트(race condition 일어나는 테스트)")
    @Test
    public void likeConcurrencyTest() throws InterruptedException {
        // given
        // beforeEach()에서 사용자, 게시글 추가

        // when
        int threadCount = DO_LIKE_SU;

        //멀티스레드 이용 ExecutorService : 비동기를 단순하게 처리할 수 있도록 해주는 java api
        ExecutorService executorService = Executors.newFixedThreadPool(32);

        //다른 스레드에서 수행이 완료될 때 까지 대기할 수 있도록 도와주는 API - 요청이 끝날때 까지 기다림
        CountDownLatch latch = new CountDownLatch(threadCount);

        log.info("좋아요 서비스 호출 반복문 시작");
        for (int i = 0; i < threadCount; i++) {
            int finalUserI = i;
            int finalBoardI = i%2;
            executorService.execute(() -> {
                try {
                    // 좋아요 서비스 호출
                    boardService.likeUseRedisson(boardList.get(finalBoardI).getId(), userList.get(finalUserI));
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();
        log.info("좋아요 서비스 호출 반복문 끝");

        // then
        Board resultBoardA = boardRepository.findById(boardList.get(0).getId()).orElseGet(null);
        Board resultBoardB = boardRepository.findById(boardList.get(1).getId()).orElseGet(null);
        // 2개 개시글에 각각 반씩 좋아요 했기 때문에 좋아요 카운트가 threadCount/2
        log.debug("테스트 수행 후 결과A :{}", resultBoardA.getLikeCount());
        log.debug("테스트 수행 후 결과B :{}", resultBoardB.getLikeCount());
        assertEquals(threadCount/2, resultBoardA.getLikeCount());
        assertEquals(threadCount/2, resultBoardB.getLikeCount());
    }

    @DisplayName("같은 사용자로 좋아요 동시 요청 테스트(race condition 일어나는 테스트)")
    @Test
    public void likeConcurrencyBySameUserTest() throws InterruptedException {
        // given
        // beforeEach()에서 사용자, 게시글 추가

        // when
        int threadCount = DO_LIKE_SU;

        //멀티스레드 이용 ExecutorService : 비동기를 단순하게 처리할 수 있도록 해주는 java api
        ExecutorService executorService = Executors.newFixedThreadPool(32);

        //다른 스레드에서 수행이 완료될 때 까지 대기할 수 있도록 도와주는 API - 요청이 끝날때 까지 기다림
        CountDownLatch latch = new CountDownLatch(threadCount);

        log.info("좋아요 서비스 호출 반복문 시작");
        Long BoardId = boardList.get(0).getId();
        for (int i = 0; i < threadCount; i++) {
            int finalI = i/2; // 같은 user로 2번씩 호출
            executorService.execute(() -> {
                try {
                    // 좋아요 서비스 호출
                    boardService.likeUseRedisson(BoardId, userList.get(finalI));
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();
        log.info("좋아요 서비스 호출 반복문 끝");

        // then
        Board resultBoard = boardRepository.findById(BoardId).orElseGet(null);
        assertEquals(0, resultBoard.getLikeCount()); // 같은 user로 2번씩 호출했기 때문에 likeCount가 0
    }

}
