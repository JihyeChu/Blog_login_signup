package com.sparta.blog.comment.service;

import com.sparta.blog.comment.dto.CommentRequestDto;
import com.sparta.blog.comment.dto.CommentResponseDto;
import com.sparta.blog.comment.entity.Comment;
import com.sparta.blog.security.UserDetailsImpl;
import com.sparta.blog.user.entity.User;


public interface CommentService {

    /*
    * 댓글 생성
    * @param id : 생성 할 댓글 id
    * @param requestDto : 댓글 생성 요청정보
    * @return : 생성된 댓글 정보
    * */
    CommentResponseDto createComment(Long id, CommentRequestDto requestDto, UserDetailsImpl userDetails);

    /*
    * 댓글 수정
    * @param id : 수정 할 댓글 id
    * @param requestDto : 댓글 수정 요청정보
    * @param user : 댓글을 수정할 유저
    * @return : 수정된 댓글 정보
    * */
    CommentResponseDto updateComment(Long commentId, CommentRequestDto requestDto, User user);

    /*
    * 댓글 삭제
    * @param id : 삭제 할 댓글 id
    * @param user : 댓글을 삭제 할 유저
    * */
    void deleteComment(Long commentId, User user);

    /*
    * 댓글 좋아요
    * @param id : 좋아요 할 댓글 id
    * @param user : 댓글을 좋아요 할 유저
    * */
    void likeComment(Long commentId, User user);

    /*
     * 댓글 좋아요 취소
     * @param id : 좋아요 취소 할 댓글 id
     * @param user : 댓글을 좋아요 취소 할 유저
     * */
    void deleteLikeComment(Long commentId, User user);

    Comment findComment(Long id);
}
