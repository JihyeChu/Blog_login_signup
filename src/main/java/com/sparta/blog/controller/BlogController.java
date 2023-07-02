package com.sparta.blog.controller;

import com.sparta.blog.dto.ApiResponseDto;
import com.sparta.blog.dto.BlogRequestDto;
import com.sparta.blog.dto.BlogResponseDto;
import com.sparta.blog.security.UserDetailsImpl;
import com.sparta.blog.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.RejectedExecutionException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;

//  생성
    @PostMapping("/blogs")
    public ResponseEntity<BlogResponseDto> createBlog(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody BlogRequestDto requestDto){
        BlogResponseDto result = blogService.createBlog(requestDto, userDetails.getUser());

        return ResponseEntity.status(201).body(result);
    }

//  전체조회
    @GetMapping("/blogs")
    public List<BlogResponseDto> getBlogs(){

        return blogService.getBlogs();
    }

//  선택조회
    @GetMapping("/blogs/{id}")
    public ResponseEntity<BlogResponseDto> getBlog(@PathVariable Long id){
        BlogResponseDto result = blogService.getBlog(id);
        return ResponseEntity.ok().body(result);
    }

//  수정
    @PutMapping( "/blogs/{id}")
    public ResponseEntity<BlogResponseDto> updateBlog(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id, @RequestBody BlogRequestDto requestDto){
       try {
           BlogResponseDto result = blogService.updateBlog(id, requestDto, userDetails.getUser());
           return ResponseEntity.ok().body(result);
       }catch (RejectedExecutionException e){
           return ResponseEntity.badRequest().build();
       }

    }

//  삭제
    @DeleteMapping("blogs/{id}")
    public ResponseEntity<ApiResponseDto> deleteBlog(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id, @RequestBody BlogRequestDto requestDto){
        try {
            blogService.deleteBlog(id, userDetails.getUser());
            return ResponseEntity.ok().body(new ApiResponseDto("삭제 성공", HttpStatus.OK.value()));
        }catch (RejectedExecutionException e){
            return ResponseEntity.badRequest().build();
        }
    }

}
