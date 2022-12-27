package com.jig.blog.service;

import com.jig.blog.model.RoleType;
import com.jig.blog.model.User;
import com.jig.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * >
 * service가 필요한 이유
 *
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
    public void save(User user) {

        String rawPassword = user.getPassword();
        String encPassword = encoder.encode(rawPassword);
        user.setPassword(encPassword);

        // TODO: 수정 필요. 일단 기본값을 USER로 넣음
        user.setRole(RoleType.USER);

        userRepository.save(user);
    }

/*    @Transactional(readOnly = true) // 이 메서드 안에서 트랜잭션이 유지되어 정합성을 지켜진다. 외부의 여러번 select 하면 같은 값이 조회된다.
    public User login(User user) {
        return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
    }*/
}
