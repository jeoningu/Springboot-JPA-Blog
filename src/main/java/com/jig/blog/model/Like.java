package com.jig.blog.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "likes") // like 키워드로 인해 테이블명 likes로 변경
public class Like extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // 프로젝트에 연결된 DB의 넘저링 전략을 따라간다.// 오라클 - 시퀀스, MySQL - auto_increment
    @Column(name = "like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @Builder
    public Like(User user, Board board) {
        this.user = user;
        this.board = board;
    }
}
