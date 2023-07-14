package com.sparta.blog.blog.entity;

import com.sparta.blog.blog.dto.BlogRequestDto;
import com.sparta.blog.comment.entity.Comment;
import com.sparta.blog.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name="memo")
@NoArgsConstructor
public class Blog extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @OneToMany(mappedBy = "blog", cascade = CascadeType.REMOVE)
    private List<Comment> commentList = new ArrayList<>();

    public Blog(BlogRequestDto requestDto){
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
    }

    public void update(BlogRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
    }

}



