package com.jig.blog.handler;

import com.jig.blog.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

// TODO : ResponseEntity 적용 필요할 듯? ajax fail에서 받는게 안 됨.
@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseDto<String> handleArgumentException(Exception e) {
        // TODO : postman에서 테스트 했을 때 error 메세지 마지막 줄만 보여서 원인 파악 제대로 안 됨
        return new ResponseDto<String>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }
}
