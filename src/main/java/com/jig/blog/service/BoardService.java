package com.jig.blog.service;

//import com.jig.blog.config.ServerSentEvents.backup.SseEmitters;
import com.jig.blog.config.ServerSentEvents.NotificationService;
import com.jig.blog.model.Board;
import com.jig.blog.model.Reply;
import com.jig.blog.model.RspNotification;
import com.jig.blog.model.User;
import com.jig.blog.repository.BoardRepository;
import com.jig.blog.repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


/**
 * service가 필요한 이유
 *
 * 1) 트랜잭션을 관리하기 위함
 * 2) service 의미
 */
@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private ReplyRepository replyRepository;

   @Autowired
   private NotificationService notificationService;

    @Transactional
    public void saveBoard(Board board, User user) {
        board.setCount(0);
        board.setUser(user);

        boardRepository.save(board);
    }

    /**
     * 게시글 목록 조회
     */
    @Transactional(readOnly = true)
    public Page<Board> getBoardList(Pageable pageable) {
        // #1. board에 @ManyToOne인 user 엔티티에 의해 일반 전체 조회 쿼리를 사용하면 N+1 문제가 발생한다.
        // 1-1 or 1-2 방법을 사용해서 해결할 수 있다.
        // 여기서는 board에는 항상 user가 있을 테니까 inner join이 되는 fetch join이 적절해 보인다.
        // Page<Board> boards = boardRepository.findAll(pageable);

        // #1-1. N+1 문제를 해결하기 위해 EntityGraph를 사용 -> from Board board0_ left outer join User
        //Page<Board> boards = boardRepository.findDistinctWithUserBy(pageable);

        // #1-2. N+1 문제를 해결하기 위해 fetch join 사용 -> from Board board0_ inner join User
        Page<Board> boards = boardRepository.findAllWithUserBy(pageable);

        // @Transactional 설정된 Service 내의 메서드에서, 정보를 미리 LOAD합니다.
        // 영속상태에서 미리 연관관계 정보를 로드해두면, LAZY 관련 에러가 발생되지 않습니다.
        boards.stream().forEach(board -> Optional.ofNullable( board.getUser()).map(User::getName));
        return boards;
    }

    /**
     * 글 상세 조회
     */
    @Transactional(readOnly = true)
    public Board getBoard(int id) {
        Board board = boardRepository.findWithUserById(id).orElseThrow(() -> {
            return new IllegalArgumentException("글 상세 보기 실패 - 찾을 수 없는 board id 입니다. : " + id);
        });

        // @Transactional 설정된 Service 내의 메서드에서, 정보를 미리 LOAD합니다.
        // 영속상태에서 미리 연관관계 정보를 로드해두면, LAZY 관련 에러가 발생되지 않습니다.
        board.getUser().getEmail();
        board.getReplies().stream().forEach(reply -> reply.getUser().getName());
        return board;
    }

    @Transactional
    public void deleteBoard(int id) {
        boardRepository.deleteById(id);
    }

    @Transactional
    public void updateBoard(int id, Board board) {

        Board findBoard = boardRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("글 수정 실패 - 찾을 수 없는 board id 입니다. : " + id);
        });

        findBoard.setTitle(board.getTitle());
        findBoard.setContent(board.getContent());
    }

    @Transactional
    public void saveReply(int boardId, Reply reply, User replyUser) {
        Board findBoard = boardRepository.findById(boardId).orElseThrow(() -> {
            return new IllegalArgumentException("댓글 추가 실패 - 찾을 수 없는 board id 입니다. : " + boardId);
        });

        reply.setUser(replyUser);
        reply.setBoard(findBoard);

        replyRepository.save(reply);

        // sse를 통해 댓글이 추가된 게시글 저자에게 알림 메세지
        // - 게시글 저자가 로그인 되어 있는 경우 게시글 저자의 화면에 알림 메세지가 발생 한다.
        RspNotification rspNotification = RspNotification.builder()
                .boardUserId(findBoard.getUser().getId())
                .boardId(findBoard.getId())
                .boardTitle(findBoard.getTitle())
                .build();
        notificationService.send(rspNotification);
    }

    @Transactional
    public void removeReply(int  replyId) {
        replyRepository.deleteById(replyId);
    }

    @Transactional
    public void modifyReply(int replyId, Reply reply) {
        Reply persistenceReply = replyRepository.findById(replyId).orElseThrow(() -> {
            return new IllegalArgumentException("댓글 수정 실패 - 찾을 수 없는 reply id 입니다. : " + replyId);
        });

        persistenceReply.setContent(reply.getContent());

    }
}
