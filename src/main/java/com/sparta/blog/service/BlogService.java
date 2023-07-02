package com.sparta.blog.service;

import com.sparta.blog.dto.BlogRequestDto;
import com.sparta.blog.dto.BlogResponseDto;
import com.sparta.blog.entity.Blog;
import com.sparta.blog.entity.User;
import com.sparta.blog.repository.BlogRepository;
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
        Blog blog = findblog(id);
        return new BlogResponseDto(blog);
    }

    @Transactional
    public BlogResponseDto updateBlog(Long id, BlogRequestDto requestDto, User user) {
        Blog blog = findblog(id);

        if(!blog.getUser().equals(user)){
            throw new RejectedExecutionException();
        }

        blog.update(requestDto);

        return new BlogResponseDto(blog);
    }

    public void deleteBlog(Long id, User user) {
        Blog blog = findblog(id);

        if(!blog.getUser().equals(user)){
            throw new RejectedExecutionException();
        }

        blogRepository.delete(blog);
    }

    private Blog findblog(Long id){
        return blogRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 메모는 존재하지 않습니다.")
        );
    }

}
