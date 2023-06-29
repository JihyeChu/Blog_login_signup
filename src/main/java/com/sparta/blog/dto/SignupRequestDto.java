package com.sparta.blog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {

    @Pattern(regexp = "^[0-9a-z]{4,10}$")
    @NotBlank
    private String username;
    @Pattern(regexp = "^[0-9a-zA-Z]{8,15}$")
    @NotBlank
    private String password;

}