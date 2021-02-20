package com.february.edsc.domain.user.like;

import com.february.edsc.domain.post.Post;
import com.february.edsc.domain.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "Liked")
public class Like {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    //==연관관계 편의 메서드==//
    public void setUser(User user) {
        this.user = user;
        user.getLikes().add(this);
    }
}