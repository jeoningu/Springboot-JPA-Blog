package com.jig.blog.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RspNotification {

    Long boardId; // 게시글 id
    Long boardUserId; // 게시글 작성자 id
    String boardTitle; // 게시글 제목
}
