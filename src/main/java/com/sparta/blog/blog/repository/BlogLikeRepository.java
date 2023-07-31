package com.sparta.blog.blog.repository;

import com.sparta.blog.blog.entity.Blog;
import com.sparta.blog.like.entity.Like;
import com.sparta.blog.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BlogLikeRepository extends JpaRepository<Like, Long> {

    boolean existsByUserAndBlog(User user, Blog blog);

    Optional<Like> findByUserAndBlog(User user, Blog blog);
}
