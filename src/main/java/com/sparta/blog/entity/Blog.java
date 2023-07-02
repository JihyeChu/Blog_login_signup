package com.sparta.blog.entity;

import com.sparta.blog.dto.BlogRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="memo")
@NoArgsConstructor
public class Blog extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="title", nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @Column(name="password", nullable = false)
    private String password;

    @Column(name="contents", nullable = false)
    private String contents;

    public Blog(BlogRequestDto requestDto){
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
    }

    public void update(BlogRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();

    }

}



