package com.jig.blog.repository;

import com.jig.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JpaRepository<T, ID> : T - 엔티티의 타입 ,ID - 엔티티에서 식별자의 타입
 *
 * # 식별자 : 엔티티의 여러 객체들을 구분하기 위한, 즉 엔티티를 대표하기 위한 속성
  */

// @Repository // bean 생성을 해주는 어노테이션이 없어도 자동으로 bean 등록이 된다.
public interface UserRepository extends JpaRepository<User, Integer> {




}
