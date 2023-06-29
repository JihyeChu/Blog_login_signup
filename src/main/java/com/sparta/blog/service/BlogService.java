package com.sparta.blog.service;

import com.sparta.blog.dto.BlogRequestDto;
import com.sparta.blog.dto.BlogResponseDto;
import com.sparta.blog.entity.Blog;
import com.sparta.blog.repository.BlogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BlogService {

    private final BlogRepository blogRepository;

    public BlogService(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }


    public BlogResponseDto createBlog(BlogRequestDto requestDto) {
        // RqDto -> Entity
        Blog blog = new Blog(requestDto);

        // DB 저장
        Blog saveBlog = blogRepository.save(blog);

        // Entity -> RsDto
        BlogResponseDto blogResponseDto = new BlogResponseDto(blog);

        return blogResponseDto;
    }

    public List<BlogResponseDto> getBlogs(){
        return blogRepository.findAllByOrderByModifiedAtDesc().stream().map(BlogResponseDto::new).toList();
    }

    public BlogResponseDto getBlog(Long id) {
        Blog blog = findblog(id);
        return new BlogResponseDto(blog);
    }

    @Transactional
    public Long updateBlog(Long id, BlogRequestDto requestDto) {
        Blog blog = findblog(id);
        blog.checkPassword(requestDto.getPassword());
        blog.update(requestDto);
        return id;
    }

    public Long deleteBlog(Long id, BlogRequestDto requestDto) {
        Blog blog = findblog(id);
        blog.checkPassword(requestDto.getPassword());
        blogRepository.delete(blog);
        return id;
    }

    private Blog findblog(Long id){
        return blogRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 메모는 존재하지 않습니다.")
        );
    }

}
