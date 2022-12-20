package com.jig.blog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 넘버링 전략을 연결된 db에 따르겠따. mysql은 auto-increament
    private int id;

    @Column(nullable = false, length = 200)
    private String content; // 섬머노트라는 라이브러리를 사용하면 일반적으로 적은 글이 <html>태그가 섞여서 디자인 됨. 따라서 데이터 용량이 커지기 때문에 대용량 데이터가 필요

    @ManyToOne
    @JoinColumn(name = "boardId")
    private Board board;

    @ManyToOne // Many = Board, One = User,  대 대 일 관계
    @JoinColumn(name = "userId")// 참조관계에서 관계형 데이터베이스는 foreign key를 사용하고 ORM에서는 객체를 사용한다. 이 때, ORM에서는 자식 테이블에서 조인 컬럼명을 지정해준다.
    private User user; // private String userId; 대신 객체와 @JoinColumn(name="userId")를 사용

    @CreationTimestamp
    private Timestamp createDate;
}
