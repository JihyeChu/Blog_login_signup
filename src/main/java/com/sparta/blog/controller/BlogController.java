package com.sparta.blog.controller;

import com.sparta.blog.dto.BlogRequestDto;
import com.sparta.blog.dto.BlogResponseDto;
import com.sparta.blog.service.BlogService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BlogController {

    private final BlogService blogService;
    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

//  생성
    @PostMapping("/blogs")
    public BlogResponseDto createBlog(@RequestBody BlogRequestDto requestDto){
        return blogService.createBlog(requestDto);
    }

//  전체조회
    @GetMapping("/blogs")
    public List<BlogResponseDto> getBlogs(){
        return blogService.getBlogs();
    }

//  선택조회
    @GetMapping("/blogs/{id}")
    public BlogResponseDto getBlog(@PathVariable Long id){
        return blogService.getBlog(id);
    }

//  수정
    @PutMapping( "/blogs/{id}")
    public Long updateBlog(@PathVariable Long id, @RequestBody BlogRequestDto requestDto){
        return blogService.updateBlog(id, requestDto);
    }

//  삭제
    @DeleteMapping("blogs/{id}")
    public Long deleteBlog(@PathVariable Long id, @RequestBody BlogRequestDto requestDto){
        return blogService.deleteBlog(id, requestDto);
    }

}
