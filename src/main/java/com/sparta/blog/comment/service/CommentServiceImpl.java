package com.sparta.blog.comment.service;

import com.sparta.blog.blog.entity.Blog;
import com.sparta.blog.blog.repository.BlogRepository;
import com.sparta.blog.comment.dto.CommentRequestDto;
import com.sparta.blog.comment.dto.CommentResponseDto;
import com.sparta.blog.comment.entity.Comment;
import com.sparta.blog.comment.repository.CommentRepository;
import com.sparta.blog.security.UserDetailsImpl;
import com.sparta.blog.user.entity.User;
import com.sparta.blog.user.entity.UserRoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.RejectedExecutionException;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

    private final BlogRepository blogRepository;
    private final CommentRepository commentRepository;

    @Override
    public CommentResponseDto createComment(Long id, CommentRequestDto requestDto, UserDetailsImpl userDetails) {
        // dto -> entity
        Blog blog = blogRepository.findById(id).orElseThrow(
                () -> new NullPointerException("찾을 수 없습니다."));
        Comment comment = new Comment(requestDto, userDetails, blog);
        commentRepository.save(comment);
        return new CommentResponseDto(comment);
    }

    @Override
    @Transactional
    public CommentResponseDto updateComment(Long commentId, CommentRequestDto requestDto, User user) {

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new NullPointerException("작성한 댓글이 없습니다.")
        );

        if(!user.getRole().equals(UserRoleEnum.ADMIN) && !comment.getUser().getUsername().equals(user.getUsername())){
            throw new RejectedExecutionException();
        }

        comment.update(requestDto);
        return new CommentResponseDto(comment);
    }

    @Override
    public void deleteComment(Long commentId, User user) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new NullPointerException("작성한 댓글이 없습니다.")
        );

        if(!user.getRole().equals(UserRoleEnum.ADMIN) && !comment.getUser().getUsername().equals(user.getUsername())){
            throw new RejectedExecutionException();
        }

        commentRepository.delete(comment);
    }

}
