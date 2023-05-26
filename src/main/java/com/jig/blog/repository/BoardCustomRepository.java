package com.jig.blog.repository;

import com.jig.blog.model.Board;

public interface BoardCustomRepository {
    void addLikeCount(Board board);

    void subtractLikeCount(Board board);
}
