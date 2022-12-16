package com.jig.blog.test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // @Getter, @Setter 합친거
//@AllArgsConstructor // 모든 변수를 인자로 하는 생성자
@NoArgsConstructor // 기본 생성자
public class Member {

    private int id;
    private String username;
    private String password;
    private String email;

    @Builder // 빌더 패턴으로 파리미터를 부분적으로 받을 수 있음
    public Member(int id, String username, String password, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
