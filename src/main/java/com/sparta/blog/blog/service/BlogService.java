package com.sparta.blog.blog.service;

import com.sparta.blog.blog.dto.BlogRequestDto;
import com.sparta.blog.blog.dto.BlogResponseDto;
import com.sparta.blog.blog.entity.Blog;
import com.sparta.blog.user.entity.User;
import com.sparta.blog.blog.repository.BlogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.RejectedExecutionException;

@Service
public class BlogService {

    private final BlogRepository blogRepository;

    public BlogService(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }


    public BlogResponseDto createBlog(BlogRequestDto requestDto, User user) {
        // RqDto -> Entity
        Blog blog = new Blog(requestDto);
        blog.setUser(user);

        // DB 저장
        blogRepository.save(blog);

        // Entity -> RsDto
        return new BlogResponseDto(blog);
    }

    public List<BlogResponseDto> getBlogs(){
        return blogRepository.findAllByOrderByModifiedAtDesc().stream().map(BlogResponseDto::new).toList();
    }

    public BlogResponseDto getBlog(Long id) {
        Blog blog = findBlog(id);
        return new BlogResponseDto(blog);
    }

    @Transactional
    public BlogResponseDto updateBlog(Long id, BlogRequestDto requestDto, User user) {
        Blog blog = findBlog(id);

        if(!user.getUsername().equals(blog.getUser().getUsername())){
            throw new RejectedExecutionException();
        }

        blog.update(requestDto);

        return new BlogResponseDto(blog);
    }

    public void deleteBlog(Long id, User user) {
        Blog blog = findBlog(id);

        if(!blog.getUser().equals(user)){
            throw new RejectedExecutionException();
        }

        blogRepository.delete(blog);
    }

    public Blog findBlog(Long id){
        return blogRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 메모는 존재하지 않습니다.")
        );
    }



}