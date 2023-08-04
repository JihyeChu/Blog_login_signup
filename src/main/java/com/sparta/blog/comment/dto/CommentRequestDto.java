package com.sparta.blog.comment.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDto {
    private Long blogId;
    private String comment;

}
