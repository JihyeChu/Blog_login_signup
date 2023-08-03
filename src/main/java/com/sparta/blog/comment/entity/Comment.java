package com.sparta.blog.comment.entity;

import com.sparta.blog.blog.entity.Blog;
import com.sparta.blog.blog.entity.Timestamped;
import com.sparta.blog.comment.dto.CommentRequestDto;
import com.sparta.blog.like.entity.CommentLike;
import com.sparta.blog.security.UserDetailsImpl;
import com.sparta.blog.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name="comment")
public class Comment extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String comment;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name="blog_id", nullable = false)
    private Blog blog;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.REMOVE)
    private List<CommentLike> commentLikes = new ArrayList<>();

    public Comment(CommentRequestDto requestDto, UserDetailsImpl userDetails, Blog blog) {
        this.comment = requestDto.getComment();
        this.username = userDetails.getUsername();
        this.user = userDetails.getUser();
        this.blog = blog;
    }

    public void update(CommentRequestDto requestDto) {
        this.comment = requestDto.getComment();

    }
}
