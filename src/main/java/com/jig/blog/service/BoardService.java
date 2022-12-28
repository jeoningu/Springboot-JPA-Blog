package com.jig.blog.service;

import com.jig.blog.model.Board;
import com.jig.blog.model.User;
import com.jig.blog.repository.BoardRepository;
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

    @Transactional
    public void save(Board board, User user) {
        board.setCount(0);
        board.setUser(user);

        boardRepository.save(board);
    }

    public Page<Board> getBoardList(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }

    public Board getBoardDetail(int id) {
        return boardRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("글 상세 보기 실패 - 찾을 수 없는 board id 입니다. : " + id );
        });
    }

}
