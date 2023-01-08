package com.jig.blog.service;

import com.jig.blog.model.Board;
import com.jig.blog.model.Reply;
import com.jig.blog.model.User;
import com.jig.blog.repository.BoardRepository;
import com.jig.blog.repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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

    @Transactional
    public void saveBoard(Board board, User user) {
        board.setCount(0);
        board.setUser(user);

        boardRepository.save(board);
    }

    @Transactional(readOnly = true)
    public Page<Board> getBoardList(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }

    /**
     *  글 상세보기
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    public Board getBoard(int id) {
        return boardRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("글 상세 보기 실패 - 찾을 수 없는 board id 입니다. : " + id );
        });
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
    public void saveReply(int boardId, Reply reply, User user) {

        Board findBoard = boardRepository.findById(boardId).orElseThrow(() -> {
            return new IllegalArgumentException("댓글 추가 실패 - 찾을 수 없는 board id 입니다. : " + boardId);
        });

        reply.setUser(user);
        reply.setBoard(findBoard);

        replyRepository.save(reply);
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
