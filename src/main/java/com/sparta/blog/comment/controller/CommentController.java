package com.sparta.blog.comment.controller;

import com.sparta.blog.ApiResponseDto;
import com.sparta.blog.comment.dto.CommentRequestDto;
import com.sparta.blog.comment.dto.CommentResponseDto;
import com.sparta.blog.comment.service.CommentService;
import com.sparta.blog.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.RejectedExecutionException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 댓글 생성
    @PostMapping("/comments/{id}")
    public ResponseEntity<CommentResponseDto> createComment(@PathVariable Long id, @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
       CommentResponseDto responseDto = commentService.createComment(id, requestDto, userDetails);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    // 댓글 수정
    @PutMapping("/comments/{comment_id}")
    public ResponseEntity<ApiResponseDto> updateComment(@PathVariable Long comment_id, @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        try{
            CommentResponseDto responseDto = commentService.updateComment(comment_id, requestDto, userDetails.getUser());
            return ResponseEntity.ok().body(new ApiResponseDto("댓글 수정 성공", HttpStatus.OK.value()));
        }catch (RejectedExecutionException e){
            return ResponseEntity.badRequest().body(new ApiResponseDto("작성자만 수정 할 수 있습니다.", HttpStatus.BAD_REQUEST.value()));
        }
    }

    // 댓글 삭제
    @DeleteMapping("/comments/{comment_id}")
    public ResponseEntity<ApiResponseDto> deleteComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long comment_id){
        try{
            commentService.deleteComment(comment_id, userDetails.getUser());
            return ResponseEntity.ok().body(new ApiResponseDto("댓글 삭제 성공", HttpStatus.OK.value()));
        }catch (RejectedExecutionException e){
            return ResponseEntity.badRequest().body(new ApiResponseDto("작성자만 삭제 할 수 있습니다.", HttpStatus.BAD_REQUEST.value()));
        }
    }

}
