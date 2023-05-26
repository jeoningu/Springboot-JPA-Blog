package com.jig.blog.repository;

import com.jig.blog.model.Board;
import com.jig.blog.model.Like;
import com.jig.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUserAndBoard(User user, Board board);

    void deleteLikeByUserAndBoard(User user, Board board);
}
