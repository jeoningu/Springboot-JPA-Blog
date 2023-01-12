package com.jig.blog.service;

import com.jig.blog.config.auth.PrincipalDetail;
import com.jig.blog.model.RoleType;
import com.jig.blog.model.User;
import com.jig.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


/**
 * >
 * service가 필요한 이유
 * <p>
 * 1) 트랜잭션을 관리하기 위함
 * 2) service 의미
 */
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Transactional
    public void joinUser(User user) {

        String rawPassword = user.getPassword();
        String encPassword = encoder.encode(rawPassword);
        user.setPassword(encPassword);

        // TODO: 수정 필요. 일단 기본값을 USER로 넣음
        user.setRole(RoleType.USER);

        userRepository.save(user);
    }

    @Transactional
    public void updateUser(User user, PrincipalDetail principalDetail) {
        // validation 체크
        // Oauth 회원인 경우 수정 못 하게 return
        if (StringUtils.hasText(user.getOauth())) {
            return;
        }

        /*
        수정 시에는 영속성 컨텍스트를 이용한다.
         1. DB에서 SELECT해서 User 오브젝트를 영속성 컨텍스트에 영속화시킨다.
         2. 영속화된 User 오브텍트를 수정한다.
         3. 영속화된 오브젝트를 변경한다.
         4. 영속성컨텍스트가 Dirty Cecking을 해서 변경을 감지하여 DB에 UPDATE문을 요청한다.
         5. 함수가 종료되면서 트랙잭션이 종료되고 DB에 COMMIT한다.
         */
        User persistenceUser = userRepository.findById(user.getId()).orElseThrow(() -> {
            return new IllegalArgumentException("회원 수정 실패 - 회원 id를 찾을 수 없습니다. : " + user.getId());
        });

        String rawPassword = user.getPassword();
        String encPassword = encoder.encode(rawPassword);
        persistenceUser.setPassword(encPassword);
        persistenceUser.setEmail(user.getEmail());

        // 변경된 회원정보 SESSION에 반영 ( 참고 :  https://azurealstn.tistory.com/92 )
        principalDetail.setUser(persistenceUser);
    }

    @Transactional(readOnly = true)
    public User getUser(String username) {
        User user = userRepository.findByUsername(username).orElseGet(()->{
            return null;
        });
        return user;
    }

/*
    @Transactional(readOnly = true) // 이 메서드 안에서 트랜잭션이 유지되어 정합성을 지켜진다. 외부의 여러번 select 하면 같은 값이 조회된다.
    public User login(User user) {
        return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
    }*/
}
