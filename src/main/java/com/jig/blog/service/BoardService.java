package com.jig.blog.service;

//import com.jig.blog.config.ServerSentEvents.backup.SseEmitters;
import com.jig.blog.dto.BoardReqDto;
import com.jig.blog.dto.ReplyReqDto;
import com.jig.blog.model.*;
import com.jig.blog.repository.BoardRepository;
import com.jig.blog.repository.LikeRepository;
import com.jig.blog.repository.ReplyRepository;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.concurrent.TimeUnit;


/**
 * service가 필요한 이유
 *
 * 1) 트랜잭션을 관리하기 위함
 * 2) service 의미
 */
@Slf4j
@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private ReplyRepository replyRepository;
    @Autowired
    private LikeRepository likeRepository;
//    @Autowired
//    private NotificationService notificationService;
    @Autowired
    private RedissonClient redissonClient;

    @Transactional
    public void saveBoard(BoardReqDto boardReqDto, User user) {
        Board board = Board.builder()
                .title(boardReqDto.getTitle())
                .content(boardReqDto.getContent())
                .build();
        board.setUser(user);

        boardRepository.saveAndFlush(board);
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
    @Transactional
    public Board getBoard(Long id, boolean isDetail) {
        Board board = boardRepository.findWithUserById(id).orElseThrow(() -> {
            return new IllegalArgumentException("글 상세 보기 실패 - 찾을 수 없는 board id 입니다. : " + id);
        });

        // @Transactional 설정된 Service 내의 메서드에서, 정보를 미리 LOAD합니다.
        // 영속상태에서 미리 연관관계 정보를 로드해두면, LAZY 관련 에러가 발생되지 않습니다.
        board.getUser().getEmail();
        board.getReplies().stream().forEach(reply -> reply.getUser().getName());

        // 상세 페이지 조회인 경우에만 조회수 증가
        if (isDetail) {
            board.setViewCount(board.getViewCount()+1);
        }
        return board;
    }

    @Transactional
    public void removeBoard(Long id) {
        boardRepository.deleteById(id);
    }

    @Transactional
    public void modifyBoard(Long id, BoardReqDto boardReqDto) {

        Board findBoard = boardRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("글 수정 실패 - 찾을 수 없는 board id 입니다. : " + id);
        });

        findBoard.setTitle(boardReqDto.getTitle());
        findBoard.setContent(boardReqDto.getContent());
    }

    @Transactional
    public void saveReply(Long boardId, ReplyReqDto replyReqDto, User replyUser) {
        Board findBoard = boardRepository.findById(boardId).orElseThrow(() -> {
            return new IllegalArgumentException("댓글 추가 실패 - 찾을 수 없는 board id 입니다. : " + boardId);
        });

        Reply reply = Reply.builder()
                .content(replyReqDto.getContent())
                .user(replyUser)
                .board(findBoard)
                .build();

        replyRepository.save(reply);

//        // sse를 통해 댓글이 추가된 게시글 저자에게 알림 메세지
//        // - 게시글 저자가 로그인 되어 있는 경우 게시글 저자의 화면에 알림 메세지가 발생 한다.
//        RspNotification rspNotification = RspNotification.builder()
//                .boardUserId(findBoard.getUser().getId())
//                .boardId(findBoard.getId())
//                .boardTitle(findBoard.getTitle())
//                .build();
//        notificationService.send(rspNotification);
    }

    @Transactional
    public void removeReply(Long  replyId) {
        replyRepository.deleteById(replyId);
    }

    @Transactional
    public void modifyReply(Long replyId, ReplyReqDto replyReqDto) {
        Reply persistenceReply = replyRepository.findById(replyId).orElseThrow(() -> {
            return new IllegalArgumentException("댓글 수정 실패 - 찾을 수 없는 reply id 입니다. : " + replyId);
        });

        persistenceReply.setContent(replyReqDto.getContent());
    }

    /*
     좋아요 기능에 대해서 redisson을 이용하여 분산 락(Distributed lock) 처리
      - 여러 프로세스,쓰레드에서 공유자원에 접근할 때 동시 접근을 제어해서 자원의 정합성이 깨지지 않도록 하는 것
      - redisson 처리 메서드에는 @Transactional을 설정하면 lock 획득,해제가 정상동작하지 않기 때문에 @Transactional을 설정하면 안됨
      - 작업 메서드 doLike 에는 @Transactional 처리
     */
    public void likeUseRedisson(Long boardId, User user) {
        //key 로 Lock 객체 가져옴
        final String lockName = "boardId-" + boardId +" like";
        final RLock lock = redissonClient.getLock(lockName);
        try {
            //획득시도 시간, 락 점유 시간
            boolean available = lock.tryLock(100, 3, TimeUnit.SECONDS);
            if (!available) {
                log.warn("lock 획득 실패");
                return;
            }

            doLike(boardId, user);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    @Transactional
    public void doLike(Long boardId, User user) {
        /*
        낙관적 락 처리시 try-catch 추가
        try {
        */
            // 게시글 검색
            /**/
            // 낙관적 락 처리 방법
            // Board findBoard = boardRepository.findByWithUserOptimisticLock(boardId).orElseThrow(() -> {
            // 비관적 락 처리 방법
            //Board findBoard = boardRepository.findByWithUserPessimisticWriteLock(boardId).orElseThrow(() -> {
            // 분산 락 처리 시에는 db에 lock 처리하지 않고 별도 저장소에 lock 처리
            Board findBoard = boardRepository.findWithUserById(boardId).orElseThrow(() -> {
                return new IllegalArgumentException("좋아요 실패 - 찾을 수 없는 board id 입니다. : " + boardId);
            });
            /*log.debug("update 수행 전 게시글 {}의 좋아요 수 : {}", findBoard.getId(), findBoard.getLikeCount());*/

            // 이미 좋아요 되어있다면 카운트 감소, 좋아요 정보 삭제
            Optional<Like> likeByUserAndBoard = likeRepository.findByUserAndBoard(user, findBoard);
            if (likeByUserAndBoard.isPresent()) {
                likeRepository.delete(likeByUserAndBoard.get());
                boardRepository.subtractLikeCount(findBoard);
            } else {

                // 좋아요 정보 저장
                Like like = Like.builder()
                        .user(user)
                        .board(findBoard)
                        .build();
                likeRepository.save(like);

                // 게시글에 좋아요 카운트 추가
                /*
                // 조회수만 1증가 시키는 간단한 예제이기 때문에 단순 쿼리로 해결할 수 있음
                findBoard.setLikeCount(findBoard.getLikeCount()+1);
                boardRepository.saveAndFlush(findBoard);
                */
                boardRepository.addLikeCount(findBoard);
            }

            /*
            Board findUpdatedBoard = boardRepository.findWithUserById(boardId).orElseThrow(() -> {
                return new IllegalArgumentException("좋아요 실패 - 찾을 수 없는 board id 입니다. : " + boardId);
            });
            log.debug("update 수행 후 게시글 {}의 좋아요 수 : {}", findUpdatedBoard.getId(), findUpdatedBoard.getLikeCount());
            */

        /*
        낙관적 락 처리시 try-catch 추가
        트랜잭션에서 board에 update할 때 optimistic lock으로 select했던 version과
        update하려는 시점의 board의 version이 다른 경우
        다른 트랜잭션에 의해 board가 update 됐다고 보고 ObjectOptimisticLockingFailureException가 발생 합니다.

        } catch (ObjectOptimisticLockingFailureException e) {
            // ObjectOptimisticLockingFailureException 발생한 좋아요 요청 트랜잭션에 대해서 처리가 필요함
        }
        */
    }
}
