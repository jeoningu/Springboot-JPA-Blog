package com.jig.blog.config.auth;

import com.jig.blog.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 스프링 시큐리티에서 사용하는 User객체에 직접 만든 User객체를 설정해준다.
 *  1. UserDetails 구현
 *  2. user객체 내부에 선언 및 생성자 추가
 *  3. UserDetails 메서드 오버라이딩
 */
public class PrincipalDetail implements UserDetails {
    private User user; // 콤포지션 ( 객체를 내부에 품는 것)

    public PrincipalDetail(User user) {
        this.user = user;
    }

    /**
     * 계정이 가지고 있는 권한 목록 반환
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collection = new ArrayList<>();

//        collection.add(new GrantedAuthority() {
//            @Override
//            public String getAuthority() {
//                return "ROLE_" + user.getRole();    // 앞에 ROLE_ 를 붙여줘야 인식할 수 있다.
//            }
//        });
        /*
         java1.8 문법 : add 안에 올 수 있는 타입은 GrantedAuthority 밖에 없고 메서드도 getAutority 하나밖에 없어서 ()->{}로 변경 가능
         */

        // role이 여러개만 반복만 필요
        collection.add(()->{
            return "ROLE_" + user.getRole(); // 앞에 ROLE_ 를 붙여줘야 인식할 수 있다.
        });

        return collection;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    /**
     * 계정이 만료된게 아닌지 ( true : 만료 안 됨)
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 계정이 잠긴게 아닌지 ( true : 안 잠김)
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 패스워드가 만료된게 아닌지 ( true : 만료 안 됨)
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 계정이 활성화 됐는지 (true : 활성화)
     * @return
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
