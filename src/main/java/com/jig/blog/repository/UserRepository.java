package com.jig.blog.repository;

import com.jig.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * JpaRepository<T, ID> : T - 엔티티의 타입 ,ID - 엔티티에서 식별자의 타입
 *
 * # 식별자 : 엔티티의 여러 객체들을 구분하기 위한, 즉 엔티티를 대표하기 위한 속성
  */

// @Repository // JpaRepository를 상속하면 bean 생성을 해주는 어노테이션이 없어도 자동으로 bean 등록이 된다.
public interface UserRepository extends JpaRepository<User, Long> {
    // 방법 1. JPA Naming 쿼리 전략 : 메서드 이름을 보고 쿼리를 만드는 것
    //User findByUsernameAndPassword(String username, String password); // SELECT * FROM user WHERE username =?1 AND password = ?2;

    /*
    // 방법2. native query : 메서드를 실행시키면 매핑해놓은 쿼리가 실행됨
    @Query(value = "SELECT * FROM user WHERE username =?1 AND password = ?2", nativeQuery = true)
    User login(String username, String password);*/

    // SELECT * FROM user WHERE username = 1?;
    Optional<User> findByUsername(String username);

    // fetch join을 사용하여 user와 board를 함께 조회하는 메서드
//    @Query("SELECT u FROM User u JOIN FETCH u.boards WHERE u.id = :userId")
//    Optional<User> findUserByIdWithBoards(@Param("userId") Long userId);

}
