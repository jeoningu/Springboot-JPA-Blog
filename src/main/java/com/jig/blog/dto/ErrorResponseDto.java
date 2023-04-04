package com.jig.blog.dto;

import com.jig.blog.error.exception.ErrorCode;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponseDto {
    private String code;        // 에러 코드
    private String message;     // 클라이언트에게 전송할 에러 메세지
    private int status;         // HTTP 상태 코드

    private ErrorResponseDto(final ErrorCode code) {
        this.code = code.getCode();
        this.message = code.getMessage();
        this.status = code.getStatus();
    }

    public static ErrorResponseDto from(final ErrorCode code) {
        return new ErrorResponseDto(code);
    }
}