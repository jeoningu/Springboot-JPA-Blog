package com.jig.blog.config.auth;

import com.jig.blog.model.User;
import com.jig.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 스프링시큐리티 로그인 처리 설정
 *  - UserDetailsService 구현
 */
@Service
public class PrincipalDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * username 확인
     *
     * 스프링이 로그인 요청을 가로챌 때, username과 password 변수2개를 가로채는데 password는 알아서 비교해준다.
     * username이 DB에 있는지만 확인해주면 된다.
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User principal = userRepository.findByUsername(username)
                .orElseThrow(()->{
                    return new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다. : " + username);
                });

        return new PrincipalDetail(principal); // 반환한 객체를 스프링 시큐리티 세션에 유저 정보( UserDatails 타입)를 저장해준다. // 직접 구현한 user 객체를 넣어서 반환하지 않으면 기본 user가 사용된다( id: user, password: 스프링 run하면 콘솔창에서 확인가능한 password)
    }
}
