package com.sparta.blog.blog.dto;

import com.sparta.blog.blog.entity.Blog;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BlogResponseDto {

    private Long id;
    private String title;
    private String contents;
    private LocalDateTime createAt;
    private LocalDateTime modified;
    private String username;

    public BlogResponseDto(Blog blog) {
        this.id = blog.getId();
        this.title = blog.getTitle();
        this.contents = blog.getContents();
        this.createAt = blog.getCreatedAt();
        this.modified = blog.getModifiedAt();
        this.username = blog.getUser().getUsername();
    }

}
