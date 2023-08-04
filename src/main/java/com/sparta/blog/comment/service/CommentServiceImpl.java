package com.sparta.blog.comment.service;

import com.sparta.blog.blog.entity.Blog;
import com.sparta.blog.blog.service.BlogService;
import com.sparta.blog.comment.dto.CommentRequestDto;
import com.sparta.blog.comment.dto.CommentResponseDto;
import com.sparta.blog.comment.entity.Comment;
import com.sparta.blog.comment.repository.CommentRepository;
import com.sparta.blog.like.entity.CommentLike;
import com.sparta.blog.like.repository.CommentLikeRepository;
import com.sparta.blog.security.UserDetailsImpl;
import com.sparta.blog.user.entity.User;
import com.sun.jdi.request.DuplicateRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

    private final BlogService blogService;
    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;

    @Override
    public CommentResponseDto createComment(CommentRequestDto requestDto, UserDetailsImpl userDetails) {
        // dto -> entity
        Blog blog = blogService.findBlog(requestDto.getBlogId());
        Comment comment = new Comment(requestDto, userDetails, blog);
        commentRepository.save(comment);
        return new CommentResponseDto(comment);
    }

    @Override
    @Transactional
    public CommentResponseDto updateComment(Comment comment, CommentRequestDto requestDto) {
        comment.update(requestDto);
        return new CommentResponseDto(comment);
    }

    @Override
    public void deleteComment(Comment comment, User user) {
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
