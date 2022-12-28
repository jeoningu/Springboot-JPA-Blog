package com.jig.blog.controller.api;

import com.jig.blog.dto.ResponseDto;
import com.jig.blog.model.User;
import com.jig.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserApiController {

    @Autowired
    private UserService userService;

//    @Autowired
//    private HttpSession httpSession;        // HttpSession 은 필드 주입

    @PostMapping("/auth/joinProc")
    public ResponseDto<Integer> save(@RequestBody User user) {
/*        System.out.println("UserApiController.save");
        System.out.println("user.getUsername() = " + user.getUsername());
        System.out.println("user.getPassword() = " + user.getPassword());
        System.out.println("user.getEmail() = " + user.getEmail());*/

        // TODO: result에 따른 처리 필요
        userService.save(user);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

//    @PostMapping("/api/user/login")
//    public ResponseDto<Integer> login(@RequestBody User user/*, HttpSession httpSession*/) {  // HttpSession은 필
//        System.out.println("UserApiController.login");
//
//        User principal = userService.login(user); // principal 접근주체라는 뜻
//
//        if (principal!=null) {
//            httpSession.setAttribute("principal", principal);
//        }
//
//        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
//    }

    @PutMapping("/user")
    public ResponseDto<Integer> userUpdate(@RequestBody User user) {

        userService.updateUser(user);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }
}
