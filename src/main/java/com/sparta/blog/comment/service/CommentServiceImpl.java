package com.sparta.blog.comment.service;

import com.sparta.blog.blog.entity.Blog;
import com.sparta.blog.blog.repository.BlogRepository;
import com.sparta.blog.comment.dto.CommentRequestDto;
import com.sparta.blog.comment.dto.CommentResponseDto;
import com.sparta.blog.comment.entity.Comment;
import com.sparta.blog.comment.repository.CommentRepository;
import com.sparta.blog.like.entity.CommentLike;
import com.sparta.blog.like.repository.CommentLikeRepository;
import com.sparta.blog.security.UserDetailsImpl;
import com.sparta.blog.user.entity.User;
import com.sparta.blog.user.entity.UserRoleEnum;
import com.sun.jdi.request.DuplicateRequestException;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.concurrent.RejectedExecutionException;

@Service
@RequriedArgsConstructor
public class CommentServiceImpl implements CommentService{

    private final BlogRepository blogRepository;
    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;

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
        Comment comment = findComment(commentId);

        if(!user.getRole().equals(UserRoleEnum.ADMIN) && !comment.getUser().getUsername().equals(user.getUsername())){
            throw new RejectedExecutionException();
        }

        comment.update(requestDto);
        return new CommentResponseDto(comment);
    }

    @Override
    public void deleteComment(Long commentId, User user) {
        Comment comment = findComment(commentId);

        if(!user.getRole().equals(UserRoleEnum.ADMIN) && !comment.getUser().getUsername().equals(user.getUsername())){
            throw new RejectedExecutionException();
        }

        commentRepository.delete(comment);
    }

    @Override
    @Transactional
    public void likeComment(Long commentId, User user){
        Comment comment = findComment(commentId);

        if(commentLikeRepository.existsByUserAndComment(user, comment)){
            throw new DuplicateRequestException("이미 좋아요 한 댓글 입니다.");
        }else{
            CommentLike commentLike = new CommentLike(user, comment);
            commentLikeRepository.save(commentLike);
        }
    }

    @Override
    @Transactional
    public void deleteLikeComment(Long id, User user){
        Comment comment = findComment(id);
        Optional<CommentLike> commentLikeOptional = commentLikeRepository.findByUserAndComment(user, comment);
        if(commentLikeOptional.isPresent()){
            commentLikeRepository.delete(commentLikeOptional.get());
        }else{
            throw new IllegalArgumentException("해당 댓글에 취소할 좋아요가 없습니다.");
        }

    }

    @Override
    public Comment findComment(Long id){
        return commentRepository.findById(id).orElseThrow(
                () -> new NullPointerException("작성한 댓글이 없습니다.")
        );
    }

}
