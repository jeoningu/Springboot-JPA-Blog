package com.jig.blog.service;

import com.jig.blog.model.User;
import com.jig.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


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

    @Transactional
    public void save(User user) {
        userRepository.save(user);
    }

}
