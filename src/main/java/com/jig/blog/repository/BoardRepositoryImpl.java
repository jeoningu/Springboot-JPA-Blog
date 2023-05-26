package com.jig.blog.repository;

import com.jig.blog.model.Board;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static com.jig.blog.model.QBoard.board;

/**
 @Repository
 JpaRepository를 구현한 인터페이스에는 @Repository를 안 붙여줘도 되지만
 직접 만든 repository에는 @Repository를 붙여줘야 한다.
 -> @Repository가 JPA, EntityManager 관련 Eception에 관한 처리를 해준다.
 */
@Repository
public class BoardRepositoryImpl implements BoardCustomRepository {

    @Autowired
    private JPAQueryFactory queryFactory;

    @Autowired
    EntityManager entityManager;

    @Override
    @Transactional
    public void addLikeCount(Board findBoard) {
        queryFactory.update(board)
                .set(board.likeCount, board.likeCount.add(1))
                .where(board.eq(findBoard))
                .execute();

        // querydsl 수정,삭제 작업시 execute() 호출
        // execute를 수행하면 수정사항을 db에만 반영, 영속성 컨텍스트에는 반영 안 함
        // 같은 트랜잭션 내에서 board 조회시 영속성컨텍스트에서 먼저 데이터를 조회 함
        // 반영되지 않은 데이터를 조회하게 됨.
        // 이 문제를 해결하기 위해 clear()를 수행해 연속성컨텍스트를 비워주면 DB에서 반영된 데이터를 조회해 옴
        ///*entityManager.flush(); // flush는 필요 없음*/
        entityManager.clear();
    }

    @Override
    @Transactional
    public void subtractLikeCount(Board findBoard) {
        queryFactory.update(board)
                .set(board.likeCount, board.likeCount.subtract(1))
                .where(board.eq(findBoard))
                .execute();

        entityManager.clear();
    }
}
