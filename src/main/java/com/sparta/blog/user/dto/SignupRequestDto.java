package com.sparta.blog.user.dto;

import com.sparta.blog.user.entity.UserRoleEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {

    @Pattern(regexp = "^[0-9a-z]{4,10}$",
            message = "최소4자 이상, 10자 이하의 알파벳 소문자(a~z), 숫자(0~9)로 구성된 username을 입력해주세요.")
    @NotBlank(message = "username을 입력해주세요.")
    private String username;
    @Pattern(regexp = "^[a-zA-Z0-9!@#$%^&*()_+{}:\"<>?,.\\\\/]{8,15}$",
            message = "최소 8자 이상, 15자 이하의 알파벳 대소문자(a~z)(A~Z), 숫자(0~9), 특수문자로 구성된 password를 입력해주세요.")
    @NotBlank(message = "password를 입력해주세요.")
    private String password;
    private UserRoleEnum role;
}