package com.sparta.blog.blog.service;

import com.sparta.blog.blog.dto.BlogRequestDto;
import com.sparta.blog.blog.dto.BlogResponseDto;
import com.sparta.blog.blog.entity.Blog;
import com.sparta.blog.user.entity.User;

import java.util.List;

public interface BlogService {

    /*
    * 게시글 생성
    * @param requestDto : 게시글 생성 요청정보
    * @param user : 게시글 생성 요청자
    * @return : 게시글 생성 결과
    * */
    BlogResponseDto createBlog(BlogRequestDto requestDto, User user);

    /*
    * 전체 게시글 목록 조회
    * @return : 전체 게시글 목록
    * */
    List<BlogResponseDto> getBlogs();

    /*
    * 게시글 한건 조회
    * @param id : 조회할 게시글 id
    * @return : 조회된 한건의 게시글
    * */
    BlogResponseDto getBlog(Long id);

    /*
    * 게시글 수정
    * @param id : 수정 할 해당 게시글 id
    * @param requestDto : 수정 할 게시글 정보
    * @param user : 게시글 수정 요청자
    * @return : 수정된 게시글 정보
    * */
    BlogResponseDto updateBlog(Long id, BlogRequestDto requestDto, User user);

    /*
    * 게시글 삭제
    * @param id : 삭제 할 해당 게시글 id
    * @param user : 게시글 삭제 요청자
    * */
    void deleteBlog(Long id, User user);

    /*
    * 게시글 좋아요
    * @param id : 좋아요 요청 게시글 id
    * @param user : 게시글 좋아요 요청 user
    * */
    void likeBlog(Long id, User user);

    /*
    * 게시글 좋아요 취소
    * @param id : 좋아요 취소 요청 게시글 id
    * @param user : 게시글 좋아요 취소 요청 user
    * */
    void dislikeBlog(Long id, User user);

    /*
    * 게시글 Entity 한건 조회
    * @param id : 조회할 게시글 id
    * @return : 게시글 entity
    * */
    Blog findBlog(Long id);



}
