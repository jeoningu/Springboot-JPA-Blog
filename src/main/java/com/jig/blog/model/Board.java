package com.jig.blog.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

//@Getter
//@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Board extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 넘버링 전략을 연결된 db에 따르겠따. mysql은 auto-increament
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Lob // 대용량 데이터
    private String content; // 섬머노트라는 라이브러리를 사용하면 일반적으로 적은 글이 <html>태그가 섞여서 디자인 됨. 따라서 데이터 용량이 커지기 때문에 대용량 데이터가 필요

    @Column(nullable = false, columnDefinition = "int default 0")
    private int viewCount; // 조회수

    @ManyToOne(fetch = FetchType.LAZY) // Many = Board, One = User,  다 대 일 관계
    @JoinColumn(name = "userId")// 관계형 데이터베이스는 객체를 저장하는게 아니라 foreign key를 사용하지만 ORM에서는 객체를 저장할 수 있다. 이 때, 조인 컬럼을 지정해준다.
    private User user; // private String userId; 대신 객체와 @JoinColumn(name="userId")를 사용

    /**
     * @ManyToOne 어노테이션을 쓰면 JPA는 한 테이블만 조홰해도 가지고 있는 객체에 대해서 join한 결과를 같이 가져온다. ( sql 사에서 select * from Board만 해도 replyList를 join을 한다는 의미 )
     * 위와 같이 동작 하는 이유는 fetch 기본값이 FetchType.EAGER이기 때문이다.
     *
     * @oneToMany는 fetch 기본값이 FetchType.LAZY이기 때문에 sql에 join이 포함되지 않는다. 만약 한번에 같이 조회하고 싶다면 fetch를 FetchType.EAGER로 변경하면 된다.
     *
     * mappedBy 옵션 : 참조관계일 때 부모테이블에서 자식테이블을 조회하기 위한 설정 ( DB에 컬럼 안 만듬),
     *                             '자식 테이블 객체'에서 참조하는 '부모 객체 필드명'을 적으면 된다.
     *  cascade 옵션 : Entity의 상태변화를 전이시킴
     *   └ REMOVE  - JPA가 관리하는 상태이긴 하지만, 실제 commmit이 일어날 때 삭제가 일어난다. 부모엔티티가 삭제되면 연관된 자식엔티티도 삭제
     *
     * @JoinColumn: 참조관계일 때 자식테이블에 FK컬럼을 만들기 위한 설정 ( DB에 컬럼 만듬 )
     */
    // One = Board, Many = Reply : 일 대 다 관계이다.
    // 게시글에서 답글을 같이 조회하고 싶은데,
    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)  // board가 삭제되면 reply도 삭제된다.
    //@JsonIgnoreProperties({"board"}) // Reply에서 board를 다시 조회하지 않겠다. // 순환참조에 의한 무한 호출을 막아준다. // 무한참조가 발생을 안하는데...?
    @OrderBy("id desc")
    //@JsonIgnore
    private List<Reply> replies;

//    @CreatedDate
////    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm")
////    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm", timezone="Asia/Seoul")
//    private String createdDate;
//
//    @LastModifiedDate   // 데이터 수정할 때 시간 자동 수정
////    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
////    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
//    private String modifiedDate;
//
//    @PrePersist
//    public void onPrePersist(){
//        this.createdDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
//        this.modifiedDate = this.createdDate;
//    }
//
//    @PreUpdate
//    public void onPreUpdate(){
//        this.modifiedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
//    }
}

/*
참고

@JsonIgnore와 @JsonIgnoreProperties는 Jackson 라이브러리에서 제공하는 어노테이션으로,
 자바 객체를 JSON 형식으로 직렬화하거나 역직렬화하는 데 사용됩니다.
  이 어노테이션들은 직렬화 또는 역직렬화 과정에서 특정 필드나 속성을 무시하도록 지정할 수 있습니다.

@JsonIgnore 어노테이션은 특정 필드나 메서드를 JSON 직렬화나 역직렬화 과정에서 무시하도록 지정합니다.
예를 들어, @JsonIgnore 어노테이션이 지정된 필드는 JSON 형식으로 변환될 때 무시되며, 역직렬화할 때는 해당 필드에 대한 값을 무시하고 객체를 생성합니다.

@JsonIgnoreProperties 어노테이션은 역직렬화 과정에서 무시할 필드나 속성을 지정하는 데 사용됩니다.
이 어노테이션은 클래스 레벨에서 지정하며, 배열 형태로 무시할 필드나 속성의 이름을 지정합니다.
 예를 들어, @JsonIgnoreProperties({"board"})와 같이 사용하면 board라는 이름의 필드나 속성이 무시됩니다.

LazyInitializationException 예외는 Hibernate에서 지연 로딩(lazy loading)을 사용할 때 발생할 수 있습니다.
Hibernate에서는 지연 로딩을 사용하여 연관된 엔티티나 컬렉션을 필요할 때까지 불러오지 않습니다.
 따라서 지연 로딩된 컬렉션을 사용하려면 Hibernate 세션이 여전히 열려 있어야 합니다.
  만약 세션이 닫혔다면 LazyInitializationException 예외가 발생할 수 있습니다.
  @JsonIgnore나 @JsonIgnoreProperties 어노테이션을 사용하여 해당 필드나 속성을 무시하면 지연 로딩된 컬렉션을 불러오지 않기 때문에
  LazyInitializationException 예외가 발생하지 않을 수 있습니다. 하지만 이 방법은 성능 이슈를 일으킬 수 있으므로 신중하게 사용해야 합니다.
 */