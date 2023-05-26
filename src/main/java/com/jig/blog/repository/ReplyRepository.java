package com.jig.blog.repository;

import com.jig.blog.model.Reply;
import com.jig.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    // todo: @Modifying 관련 추가 공부 필요할 듯
    @Modifying // 데이터를 벌크로 수정하는 임의 작성 jpql에 붙여주는 어노테이션이고 영속성 관리에 대한 옵션 설정이 필요 할 수 있음
    @Query("delete from Reply b where b.user.id = :userId")
    int deleteAllByUserId(@Param("userId") Long userId);

    List<Reply> findAllByOrderByCreatedDateDesc();
}
