package com.jig.blog.repository;

import com.jig.blog.model.Board;
import com.jig.blog.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * JpaRepository<T, ID> : T - 엔티티의 타입 ,ID - 엔티티에서 식별자의 타입
 * # 식별자 : 엔티티의 여러 객체들을 구분하기 위한, 즉 엔티티를 대표하기 위한 속성
 */

// @Repository // JpaRepository를 상속하면 bean 생성을 해주는 어노테이션이 없어도 자동으로 bean 등록이 된다.
public interface BoardRepository extends JpaRepository<Board, Long> {

    // #1. N+1 문제 해결을 위해 EntityGraph 또는 fetch join 사용
/*
    // #1-1.만들어지는 쿼리 : left outer join
    @EntityGraph(attributePaths = "user")
    Page<Board> findAllDistinctWithUserBy(Pageable pageable);
*/
    // #1-2. 만들어지는 쿼리 : from Board board0_ inner join User user1_ on board0_.userId=user1_.id
    @Query(
            value = "select s from Board s join fetch s.user",
            countQuery = "select count(s) from Board s"
    )
    Page<Board> findAllWithUserBy(Pageable pageable);

    @Query("select s from Board s join fetch s.user where s.id = :id")
    Optional<Board> findWithUserById(Long id);

//    // fetch join을 사용하여 board와 reply를 함께 조회하는 메서드
//    @Query("SELECT b FROM Board b JOIN FETCH b.replies WHERE b.id = :boardId")
//    Optional<Board> findBoardByIdWithReplies(@Param("boardId") int boardId);

    // todo: @Modifying 관련 추가 공부 필요할 듯
    @Modifying // 데이터를 벌크로 수정하는 임의 작성 jpql에 붙여주는 어노테이션이고 영속성 관리에 대한 옵션 설정이 필요 할 수 있음
    @Query("delete from Board b where b.user.id = :userId")
    int deleteAllByUserId(@Param("userId") Long userId);
}
