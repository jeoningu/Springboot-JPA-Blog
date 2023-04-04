package com.jig.blog.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserMeReqDto {
    private String name;
    private String email;
    private String currentPassword;
    private String newPassword;
}
