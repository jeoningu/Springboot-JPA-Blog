package com.jig.blog.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

// ORM : Object와 관계형 데이터베이스의 테이블을 자동으로 매핑해주는 기술
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@AllArgsConstructor
//@Builder // 빌더 패턴!!
@Entity // User 클래스에 대해서 MySQL에 테이블을 생성한다.
//@DynamicInsert // User 클래스 insert SQL 에서 값이 null인 필드는 빼준다.
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 프로젝트에 연결된 DB의 넘저링 전략을 따라간다.// 오라클 - 시퀀스, MySQL - auto_increment
    private int id;

    @Column(nullable = false, length = 100, unique = true) // nuallable : 필수값, 길이 30
    private String username; // 아이디

    @Column(nullable = false, length = 100) // nuallable : 필수값, 길이 100(hash로 비밀번호 암호화하면 길이가 길어짐
    private String password;

    @Column(nullable = false, length = 50) // nuallable : 필수값, 길이 50
    private String name;

    @Column(nullable = false, length = 50) // nuallable : 필수값, 길이 50
    private String email;

    // 방법 1. @ColumnDefault("'user'") // db에서 varchar로 받을 기본값은 ''으로 감싸준다. // insert에 기본값을 넣어주려면 클래스에 @DynamicInsert을 설정해줘야한다.
    // 방법 2. admin, manager, user와 같은 스트링 값은 오타로 입력될 수 있으므로 enum을 사용하고, 기본값을 넣어줄 수 없으므로 insert시에 직접 값으로 넣어준다.
    @Enumerated(EnumType.STRING) // enum타입은 db에 없기 때문에 String으로 인식시켜준다.
    private RoleType role;

    private String provider; // 어떤 OAuth2 인증인지 ex)google, naver, kakao, facebook    (일반 회원이면 null)

    private String providerId; // OAuth2 인증에서 사용되는 각 유저의 primary key ex)google에서는 sub값

    @CreationTimestamp // 시간 자동 입력
    private Timestamp createDate;

    @Builder
    public User(String username, String password, String name, String email, RoleType role, String provider, String providerId) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
    }
}
