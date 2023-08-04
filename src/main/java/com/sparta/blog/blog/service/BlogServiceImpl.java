package com.sparta.blog.blog.service;

import com.sparta.blog.blog.dto.BlogRequestDto;
import com.sparta.blog.blog.dto.BlogResponseDto;
import com.sparta.blog.blog.entity.Blog;
import com.sparta.blog.blog.repository.BlogLikeRepository;
import com.sparta.blog.blog.repository.BlogRepository;
import com.sparta.blog.like.entity.BlogLike;
import com.sparta.blog.user.entity.User;
import com.sun.jdi.request.DuplicateRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService{

    private final BlogRepository blogRepository;
    private final BlogLikeRepository blogLikeRepository;

    @Override
    public BlogResponseDto createBlog(BlogRequestDto requestDto, User user) {
        // RqDto -> Entity
        Blog blog = new Blog(requestDto);
        blog.setUser(user);

        // DB 저장
        blogRepository.save(blog);

        // Entity -> RsDto
        return new BlogResponseDto(blog);
    }

    @Override
    public List<BlogResponseDto> getBlogs(){
        return blogRepository.findAllByOrderByModifiedAtDesc().stream().map(BlogResponseDto::new).toList();
    }

    @Override
    public BlogResponseDto getBlog(Long id) {
        Blog blog = findBlog(id);
        return new BlogResponseDto(blog);
    }

    @Override
    @Transactional
    public BlogResponseDto updateBlog(Blog blog, BlogRequestDto requestDto, User user) {
        blog.update(requestDto);

        return new BlogResponseDto(blog);
    }

    @Override
    public void deleteBlog(Blog blog, User user) {
        blogRepository.delete(blog);
    }

    @Override
    public void likeBlog(Long id, User user){
        Blog blog = findBlog(id);

        if(blogLikeRepository.existsByUserAndBlog(user, blog)){
            throw new DuplicateRequestException("이미 좋아요 한 게시글 입니다.");
        }else{
            BlogLike like = new BlogLike(user, blog);
            blogLikeRepository.save(like);
        }
    }

    @Override
    public void dislikeBlog(Long id, User user){
        Blog blog = findBlog(id);
        Optional<BlogLike> likeOptional = blogLikeRepository.findByUserAndBlog(user, blog);
        if(likeOptional.isPresent()){
            blogLikeRepository.delete(likeOptional.get());
        }else{
            throw new IllegalArgumentException("해당 게시글에 취소할 좋아요가 없습니다.");
        }
    }

    @Override
    public Blog findBlog(Long id){
        return blogRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 메모는 존재하지 않습니다.")
        );
    }
}
