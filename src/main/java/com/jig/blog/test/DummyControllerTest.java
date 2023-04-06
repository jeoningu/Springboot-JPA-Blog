package com.jig.blog.test;

import com.jig.blog.model.RoleType;
import com.jig.blog.model.User;
import com.jig.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@RestController
public class DummyControllerTest {

    @Autowired
    private UserRepository userRepository;

    /**
     * repository.save(Entity) 함수
     *     1. Entity에 id를 넣어주지 않거나, DB에서 찾을 수 없는 id를 넣어준 경우 sql의 insert 기능을 한다.
     *     2. Entity에 DB에서 찾을 수 있는 id를 넣어준 경우 sql의 update 기능을 한다.
     */

    /**
     * 회원 가입
     * @param user
     * @return
     */
    @PostMapping("/dummy/join")
    public String join(User user) {

        System.out.println("id : " + user.getId());
        System.out.println("username : " + user.getUsername());
        System.out.println("password : " + user.getPassword());
        System.out.println("email : " + user.getEmail());
        System.out.println("role : " + user.getRole());
        System.out.println("createDate : " + user.getCreatedDate());

        user.setRole(RoleType.USER);
        userRepository.save(user);

        return "회원가입이 완료 되었습니다.";
    }

    /**
     * 회원 수정
     * @param id
     * @param requestUser
     * @return
     */
    @PutMapping("/dummy/user/{id}")
    @Transactional // 트랜잭셔널 어노테이션을 설정하면 영속성 컨테이너에 의해 변경 감지(Dirty Checking)가 일어난다.
    /**
     * 변경 감지(Dirty Checking)
     *  - 트랜잭션 안에서 엔티티의 변경을 감지하여 변경 내용을 자동으로 DB에 반영한다.
     *   1) 트랜잭션이 종료되기전 flush()가 일어나는 시점에 update query를 생성하여 쓰기지연 SQL저장소에 보낸다.
     *   2) 트랜잭션이 commit() 되는 시점에 DB로 update query를 전송한다.
     */
    public User updateUser(@PathVariable Long id, @RequestBody User requestUser) {
        User findUser = userRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("수정할 데이터를 찾을 수 없습니다.");
        });

        findUser.setPassword(requestUser.getPassword());
        findUser.setEmail(requestUser.getEmail());

        return findUser;
    }
/*
    트랜잭션 어노테이션 없이 직접 save해주는 방법 - 직접 save해주는 것보다 Dirty Checking을 사용하면 편하게 update가 가능
    @PutMapping("/dummy/user/{id}")
    public String updateUser(@PathVariable int id, @RequestBody User requestUser) {
        User findUser = userRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("수정할 데이터를 찾을 수 없습니다.");
        });

        findUser.setPassword(requestUser.getPassword());
        findUser.setEmail(requestUser.getEmail());

        userRepository.save(findUser);

        return "ok";
    }*/

    /**
     * 회원 삭제
     * @param id
     * @return
     */
    @DeleteMapping("/dummy/user/{id}")
    public String deleteUser(@PathVariable Long id) {
        try {
            userRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            return "삭제에 실패하였습니다. " + id + "(은)는 DB에 없는 id입니다.";
        }

        return "삭제 되었습니다. id : " + id;
    }

    /**
     * 회원 상세 조회
     * @param id
     * @return
     */
    @GetMapping("/dummy/user/{id}")
    public User detail(@PathVariable Long id) {

        /* User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalStateException>() {
            @Override
            public IllegalStateException get() {
                return new IllegalStateException("해당 유저는 없습니다. id = " + id);
            }
        }); */
        // java 1.8 람다식 이용한 표현
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalStateException("해당 유저는 없습니다. id = " + id));


        /*
            객체를 리턴하면 스프링 부트(Message Converter)가 웹브라우저가 이해할 수 있는 json으로 변환해서 반환한다.
         */
        return user;
    }

    /**
     * 회원 목록 조회
     * @return
     */
    @GetMapping("/dummy/users")
    public List<User> list() {
        return userRepository.findAll();
    }

    /**
     * 회원 페이지 조회 - 페이지당 2개 데이터, id 기준 내림차순(최신순) 정렬
     *
     *   http://localhost:8000/blog/dummy/user?page=0
     *     page를 쿼리스트링으로 받을 수 있다. 0부터 시작한다.
     *
     * @param pageable
     * @return
     */
    @GetMapping("/dummy/user")
    public List<User> pageList(@PageableDefault(size = 2, sort = "id", direction = Sort.Direction.DESC) Pageable pageable){

        // Page
        Page<User> userPage = userRepository.findAll(pageable);

        List<User> users = userPage.getContent();
        return users;
    }


}
