package com.jig.blog.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 넘버링 전략을 연결된 db에 따르겠따. mysql은 auto-increament
    private int id;

    @Column(nullable = false, length = 100)
    private String title;

    @Lob // 대용량 데이터
    private String content; // 섬머노트라는 라이브러리를 사용하면 일반적으로 적은 글이 <html>태그가 섞여서 디자인 됨. 따라서 데이터 용량이 커지기 때문에 대용량 데이터가 필요

    // 직접 넣어주자. @ColumnDefault("0")  // int니까 ''으로 감싸줄 필요 없음
    private int count;

    @ManyToOne // Many = Board, One = User,  다 대 일 관계
    @JoinColumn(name = "userId")// 관계형 데이터베이스는 객체를 저장하는게 아니라 foreign key를 사용하지만 ORM에서는 객체를 저장할 수 있다. 이 때, 조인 컬럼을 지정해준다.
    private User user; // private String userId; 대신 객체와 @JoinColumn(name="userId")를 사용

    /**
     * ManyToOne 어노테이션을 쓰면 JPA는 한테이블만 조홰해도 가지고 있는 객체에 대해서 join한 결과를 같이 가져온다. ( sql 사에서 select * from Board만 해도 replyList를 join을 한다는 의미 )
     * 위와 같이 동작 하는 이유는 fetch 기본값이 FetchType.EAGER이기 때문이다.
     *
     * oneToMany는 fetch 기본값이 FetchType.LAZY이기 때문에 sql에 join이 포함되지 않는다. 만약 한번에 같이 조회하고 싶다면 fetch를 FetchType.EAGER로 변경하면 된다.
     *
     * mappedBy : 참조관계일 때 부모테이블에서 자식테이블을 조회하기 위한 설정 ( DB에 컬럼 안 만듬),
     *            '자식 테이블 객체'에서 참조하는 '부모 객체 필드명'을 적으면 된다.
     * JoinColumn: 참조관계일 때 자식테이블에 FK컬럼을 만들기 위한 설정 ( DB에 컬럼 만듬 )
     */
    //  One = Board, Many = Reply : 일 대 다 관계이다.// 게시글에서 답글을 같이 조회하고 싶은데,
    // OneToMany는 fetch 기본값이 LAZY라서 EAGER로 바꿔줬따.
    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"board"}) // Reply에서 board를 다시 조회하지 않겠다. // 순환참조에 의한 무한 호출을 막아준다.
    private List<Reply> replys;

    @CreationTimestamp
    private Timestamp createDate;

}
