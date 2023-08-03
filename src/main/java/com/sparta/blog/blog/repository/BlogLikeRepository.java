package com.sparta.blog.blog.repository;

import com.sparta.blog.blog.entity.Blog;
import com.sparta.blog.like.entity.BlogLike;
import com.sparta.blog.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BlogLikeRepository extends JpaRepository<BlogLike, Long> {

    boolean existsByUserAndBlog(User user, Blog blog);

    Optional<BlogLike> findByUserAndBlog(User user, Blog blog);
}
