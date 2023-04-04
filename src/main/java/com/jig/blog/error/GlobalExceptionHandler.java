package com.jig.blog.error;

import com.jig.blog.dto.ErrorResponseDto;
import com.jig.blog.error.exception.BusinessException;
import com.jig.blog.error.exception.EntityNotFoundException;
import com.jig.blog.error.exception.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;


@ControllerAdvice
@RestController
public class GlobalExceptionHandler {
    private Logger log = LoggerFactory.getLogger(getClass());

//    @ExceptionHandler(value = Exception.class)
//    public ResponseDto<String> handleArgumentException(Exception e) {
//        log.error("{}",e.getStackTrace());
//        log.debug("{}",e);
//        return new ResponseDto<String>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
//    }

//    @ExceptionHandler(PasswordMismatchException.class)
////    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ResponseEntity<String> handlePasswordMismatchException(PasswordMismatchException e) {
//        ErrorResponseDto<String> responseDto = new ErrorResponseDto<String>(HttpStatus.BAD_REQUEST.value(), e.toString(), "InvalidPassword", e.getMessage());
//        return new ResponseEntity<>(e.toString(), HttpStatus.BAD_REQUEST);
//    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<ErrorResponseDto> handleEntityNotFoundException(
            final EntityNotFoundException exception) {
        log.error("handleEntityNotFoundException", exception);
        final ErrorCode errorCode = exception.getErrorCode();
        final ErrorResponseDto response = ErrorResponseDto.from(errorCode);
        return new ResponseEntity<>(response, HttpStatus.valueOf(errorCode.getStatus()));
    }

    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ErrorResponseDto> handleBusinessException(
            final BusinessException exception) {
        log.error("handleBusinessException", exception);
        final ErrorCode errorCode = exception.getErrorCode();
        final ErrorResponseDto response = ErrorResponseDto.from(errorCode);
        return new ResponseEntity<>(response, HttpStatus.valueOf(errorCode.getStatus()));
    }
}
