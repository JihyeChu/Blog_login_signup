package com.sparta.blog.dto;

import com.sparta.blog.entity.Blog;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BlogResponseDto {

    private Long id;
    private String title;
    private String username;
    private String password;
    private String contents;
    private LocalDateTime createAt;
    private LocalDateTime modified;

    public BlogResponseDto(Blog blog) {
        this.id = blog.getId();
        this.title = blog.getTitle();
        this.username = blog.getUsername();
        this.password = blog.getPassword();
        this.contents = blog.getContents();
        this.createAt = blog.getCreatedAt();
        this.modified = blog.getModifiedAt();
    }

}
