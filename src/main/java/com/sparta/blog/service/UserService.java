package com.sparta.blog.service;

import com.sparta.blog.dto.LoginRequestDto;
import com.sparta.blog.dto.SignupRequestDto;
import com.sparta.blog.entity.User;
import com.sparta.blog.jwt.JwtUtil;
import com.sparta.blog.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public void signup(SignupRequestDto requestDto){
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());

        // 회원 중복 확인
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if(checkUsername.isPresent()){
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        // 사용자 등록
        User user = new User(username, password);
        userRepository.save(user);

    }


    public void login(LoginRequestDto requestDto) {

        String username = requestDto.getUsername();
        String password = requestDto.getPassword();

        // user 확인
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );

        // 비밀번호 확인
        if(!passwordEncoder.matches(password, user.getPassword())){
            throw  new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

    }
}
