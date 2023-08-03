package com.sparta.blog.comment.dto;

import com.sparta.blog.comment.entity.Comment;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentResponseDto {

    private String comment;
    private Integer likeCount;


    public CommentResponseDto(Comment comment){
        this.comment = comment.getComment();
        this.likeCount = comment.getCommentLikes().size();
    }

}
