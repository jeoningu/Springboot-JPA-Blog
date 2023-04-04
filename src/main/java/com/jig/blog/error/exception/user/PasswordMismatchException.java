package com.jig.blog.error.exception.user;

import com.jig.blog.error.exception.BusinessException;
import com.jig.blog.error.exception.ErrorCode;

public class PasswordMismatchException extends BusinessException {
    public PasswordMismatchException() {
        super(ErrorCode.PASSWORD_MISMATCH);
    }
}
