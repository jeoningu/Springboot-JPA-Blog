package com.jig.blog.controller.api;

import com.jig.blog.dto.ResponseDto;
import com.jig.blog.model.RoleType;
import com.jig.blog.model.User;
import com.jig.blog.repository.UserRepository;
import com.jig.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserApiController {

    @Autowired
    private UserService userService;

    @PostMapping("/api/user")
    public ResponseDto<Integer> save(@RequestBody User user) {
        System.out.println("UserApiController.save");
        System.out.println("user.getUsername() = " + user.getUsername());
        System.out.println("user.getPassword() = " + user.getPassword());
        System.out.println("user.getEmail() = " + user.getEmail());

        // TODO: 수정 필요. 일단 기본값을 USER로 넣음
        user.setRole(RoleType.USER);

        // TODO: result에 따른 처리 필요
        userService.save(user);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }
}
