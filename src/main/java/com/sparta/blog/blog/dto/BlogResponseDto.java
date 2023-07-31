package com.sparta.blog.blog.dto;

import com.sparta.blog.blog.entity.Blog;
import com.sparta.blog.comment.dto.CommentResponseDto;
import com.sparta.blog.comment.entity.Comment;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class BlogResponseDto {

    private Long id;
    private String title;
    private String contents;
    private Integer likeCount;
    private LocalDateTime createAt;
    private LocalDateTime modified;
    private String username;
    private List<CommentResponseDto> commentResponseDto;

    public BlogResponseDto(Blog blog) {
        this.id = blog.getId();
        this.title = blog.getTitle();
        this.contents = blog.getContents();
        this.likeCount = blog.getLike().size();
        this.createAt = blog.getCreatedAt();
        this.modified = blog.getModifiedAt();
        this.username = blog.getUser().getUsername();
        this.commentResponseDto = new ArrayList<>();
        for(Comment comment : blog.getCommentList()){
            commentResponseDto.add(new CommentResponseDto(comment));
        }

    }

}
