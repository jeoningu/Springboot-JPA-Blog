package com.jig.blog.error.exception.user;

import com.jig.blog.error.exception.EntityNotFoundException;
import com.jig.blog.error.exception.ErrorCode;

public class UserNotFoundException extends EntityNotFoundException {

    public UserNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND);
    }
}