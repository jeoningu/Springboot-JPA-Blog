package com.jig.blog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

// ORM : Object와 관계형 데이터베이스의 테이블을 자동으로 매핑해주는 기술
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder // 빌더 패턴!!
@Entity // User 클래스에 대해서 MySQL에 테이블을 생성한다.
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 프로젝트에 연결된 DB의 넘저링 전략을 따라간다.// 오라클 - 시퀀스, MySQL - auto_increment
    private int id;

    @Column(nullable = false, length = 30) // nuallable : 필수값, 길이 30
    private String username; // 아이디

    @Column(nullable = false, length = 100) // nuallable : 필수값, 길이 100(hash로 비밀번호 암호화하면 길이가 길어짐
    private String password;

    @Column(nullable = false, length = 50) // nuallable : 필수값, 길이 50
    private String email;

    // admin, manager, user와 같은 스트링 값은 오타로 입력될 수 있으므로 enum을 사용하는게 좋다. 그렇지만, 이번에는 String을 사용해보겠다.
    @ColumnDefault("'user'") // db에서 varchar로 받을 기본값은 ''으로 감싸준다.
    private String role;

    @CreationTimestamp // 시간 자동 입력
    private Timestamp createDate;


}
