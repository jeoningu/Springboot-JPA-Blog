package com.jig.blog.service;

import com.jig.blog.config.security.PrincipalDetail;
import com.jig.blog.dto.UserMeReqDto;
import com.jig.blog.error.exception.user.PasswordMismatchException;
import com.jig.blog.error.exception.user.UserNotFoundException;
import com.jig.blog.model.Board;
import com.jig.blog.model.RoleType;
import com.jig.blog.model.User;
import com.jig.blog.repository.BoardRepository;
import com.jig.blog.repository.ReplyRepository;
import com.jig.blog.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


/**
 * >
 * service가 필요한 이유
 * <p>
 * 1) 트랜잭션을 관리하기 위함
 * 2) service 의미
 */
@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * 회원 가입
     */
    @Transactional
    public void joinUser(User user) {

        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        user.setPassword(encPassword);

        // TODO: 수정 필요. 일단 기본값을 USER로 넣음
        user.setRole(RoleType.USER);

        userRepository.save(user);
    }

    /**
     * 회원 정보 수정 - 이름, 이메일
     *  - 클라이언트에서 한 항목씩만 요청한다.
     */
    @Transactional
    public User updateUser(UserMeReqDto userMeReqDto) {
        /*
        수정 시에는 영속성 컨텍스트를 이용한다.
         1. DB에서 SELECT해서 User 오브젝트를 영속성 컨텍스트에 영속화시킨다.
         2. 영속화된 User 오브텍트를 수정한다.
         3. 영속화된 오브젝트를 변경한다.
         4. 영속성컨텍스트가 Dirty Cecking을 해서 변경을 감지하여 DB에 UPDATE문을 요청한다.
         5. 함수가 종료되면서 트랙잭션이 종료되고 DB에 COMMIT한다.
         */
        User currentUser = getCurrentUser();
        String name = userMeReqDto.getName();
        String email = userMeReqDto.getEmail();
        if (null != name) {
            currentUser.setName(name);

        } else if (null != email) {
            currentUser.setEmail(email);
        }

        // security context session에 반영
        setCurrentUser(currentUser);
        return currentUser;
    }
    /**
     * 회원 정보 수정 - 회원 비밀번호 수정
     */
    @Transactional
    public void updateUserPassword(UserMeReqDto userMeReqDto) {
        User currentUser = getCurrentUser();
        if (bCryptPasswordEncoder.matches(userMeReqDto.getCurrentPassword(), currentUser.getPassword())) {
            currentUser.setPassword(bCryptPasswordEncoder.encode(userMeReqDto.getNewPassword()));
        } else {
            throw new PasswordMismatchException();
        }
    }

    /**
     * 회원 탈퇴
     *  - oAuth 회원이 아닌 경우에만 패스워드 체크
     */
    @Transactional
    public void deleteUser(UserMeReqDto userMeReqDto) {
        User currentUser = getCurrentUser();

        // oAuth 회원이 아닌 경우에만 패스워드 체크
        if (null == currentUser.getProvider()) {
            if (!bCryptPasswordEncoder.matches(userMeReqDto.getCurrentPassword(), currentUser.getPassword())) {
                throw new PasswordMismatchException();
            }
        }

        /*// board 지우면서 알아서 연관관계 데이터 지워지도록.. -> 참조 데이터 삭제 안되는 sql 에러 발생함
        userRepository.deleteById(currentUser.getId());*/

        /*// user와 board, reply를 함께 조회하여 CascadeType.REMOVE에 의해 삭제합니다. -> N+1 문제 발생
        Optional<User> optionalUser = userRepository.findUserByIdWithBoards(currentUser.getId());
        optionalUser.ifPresent(user -> {
            List<Board> boards = user.getBoards();
            for (Board board : boards) {
                Optional<Board> optionalBoard = boardRepository.findBoardByIdWithReplies(board.getId());
                optionalBoard.ifPresent(b -> replyRepository.deleteAll(b.getReplies()));
            }
            boardRepository.deleteAll(boards);
            userRepository.delete(user);
        });*/

        // 참조 되는 데이터부터 지운다.
        // jpql로 작성된 delete 문을 통해 N+1 로 delete 쿼리가 발생하지 않도록 한다.
        replyRepository.deleteAllByUserId(currentUser.getId());
        boardRepository.deleteAllByUserId(currentUser.getId());
        userRepository.delete(currentUser);

        // session에서 지우기
        SecurityContextHolder.clearContext();
    }

    /**
     * 로그인된 유저 조회
     */
    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }
        String username = authentication.getName();
        return userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException());
    }

    /**
     * 스프링시큐리티 세션에 유저정보 업데이트
     */
    private void setCurrentUser(User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(new PrincipalDetail(user), null, authentication.getAuthorities()));
    }

    /**
     * username으로 db에서 user정보 조회
     */
    @Transactional(readOnly = true)
    public User getUser(String username) {
        User user = userRepository.findByUsername(username).orElseGet(()->{
            return null;
        });
        return user;
    }

/*
    @Transactional(readOnly = true) // 이 메서드 안에서 트랜잭션이 유지되어 정합성을 지켜진다. 외부의 여러번 select 하면 같은 값이 조회된다.
    public User login(User user) {
        return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
    }*/
}
