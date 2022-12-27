package com.jig.blog.service;

import com.jig.blog.model.Board;
import com.jig.blog.model.RoleType;
import com.jig.blog.model.User;
import com.jig.blog.repository.BoardRepository;
import com.jig.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


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

    public List<Board> getList() {
        return boardRepository.findAll();
    }

}
