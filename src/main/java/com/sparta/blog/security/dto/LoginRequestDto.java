package com.sparta.blog.security.dto;

import com.sparta.blog.user.entity.UserRoleEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDto {

    private String username;
    private String password;
    private UserRoleEnum role;
}
