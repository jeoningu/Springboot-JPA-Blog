package com.jig.blog.handler;

import com.jig.blog.dto.ResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.logging.LoggingSystem;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;


// TODO : ResponseEntity 적용 필요할 듯? ajax fail에서 받는게 안돼서 일단 주석 처리함.
//@ControllerAdvice
//@RestController
public class GlobalExceptionHandler {
    private Logger log = LoggerFactory.getLogger(getClass());

    //@ExceptionHandler(value = Exception.class)
    public ResponseDto<String> handleArgumentException(Exception e) {
        log.error("{}",e.getStackTrace());
        log.debug("{}",e);
        return new ResponseDto<String>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
    }
}
