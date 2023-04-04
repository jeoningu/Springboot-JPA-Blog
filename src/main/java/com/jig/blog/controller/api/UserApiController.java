package com.jig.blog.controller.api;

import com.jig.blog.dto.UserMeReqDto;
import com.jig.blog.model.User;
import com.jig.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserApiController {

    @Autowired
    private UserService userService;

//    @Autowired
//    private HttpSession httpSession;        // HttpSession 은 필드 주입

    @PostMapping("/auth/joinProc")
    public ResponseEntity<String> join(@RequestBody User user) {
        userService.joinUser(user);
        return ResponseEntity.status(HttpStatus.OK).body("");
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

    @PutMapping("/me")
    public ResponseEntity<?> userMeUpdate(@RequestBody UserMeReqDto userMeReqDto) {

        // 이름, 이메일 변경
        if (null != userMeReqDto.getName() || null != userMeReqDto.getEmail()) {
            User updateUser = userService.updateUser(userMeReqDto);
            return ResponseEntity.status(HttpStatus.OK).body(updateUser);

        // 패스워드 변경
        } else if (null != userMeReqDto.getCurrentPassword() && null != userMeReqDto.getNewPassword()) {     // 패스워드 변경
            userService.updateUserPassword(userMeReqDto); // 비밀번호가 일치하지 않는 경우 exception 발생
        }

        return ResponseEntity.status(HttpStatus.OK).body("");
    }

    @DeleteMapping("/me")
    public ResponseEntity<?> userMeDelete(@RequestBody UserMeReqDto userMeReqDto) {

        userService.deleteUser(userMeReqDto);

        return ResponseEntity.status(HttpStatus.OK).body("");
    }
}
