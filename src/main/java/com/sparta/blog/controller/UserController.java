package com.sparta.blog.controller;

import com.sparta.blog.dto.ApiResponseDto;
import com.sparta.blog.dto.LoginRequestDto;
import com.sparta.blog.dto.SignupRequestDto;
import com.sparta.blog.jwt.JwtUtil;
import com.sparta.blog.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/user/signup")
    public ResponseEntity<ApiResponseDto> signup(@RequestBody @Valid SignupRequestDto requestDto) {

        userService.signup(requestDto);

        return ResponseEntity.status(201).body(new ApiResponseDto("회원가입 성공", HttpStatus.CREATED.value()));
    }

    @PostMapping("/user/login")
    public ResponseEntity<ApiResponseDto> login(@RequestBody LoginRequestDto requestDto, HttpServletResponse response){
        userService.login(requestDto);

        // JWT 생성 및 쿠키에 저장 후 Response 객체에 추가
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(requestDto.getUsername()));

        return ResponseEntity.ok().body(new ApiResponseDto("로그인 성공", HttpStatus.CREATED.value()));
    }
}
