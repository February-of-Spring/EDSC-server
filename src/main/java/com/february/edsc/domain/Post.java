package com.february.edsc.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Post {

    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    private String title;
    private String content;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "modified_at")
    private LocalDateTime modified_At;

    @Column(name = "like_count")
    private int likeCount;

    @Column(name = "view_count")
    private int viewCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<Image> images = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<File> files = new ArrayList<>();

    //==연관관계 편의 메서드==//
    public void setUser(User user) {
        this.user = user;
        user.getPosts().add(this);
    }

    public void setCategory(Category category) {
        this.category = category;
        category.getPosts().add(this);
    }

    public void addComment(Comment comment) {
        comments.add(comment);
        comment.setPost(this);
    }

    public void addImage(Image image) {
        images.add(image);
        image.setPost(this);
    }

    public void addFile(File file) {
        files.add(file);
        file.setPost(this);
    }

    //==생성 메서드==//
    public static Post createPost(User user, Category category, String title, String content, Image[] images, File[] files) {
        Post post = new Post();
        post.setUser(user);
        post.setCategory(category);
        post.setTitle(title);
        post.setContent(content);
        for (Image image : images) {
            post.addImage(image);
        }
        for (File file : files) {
            post.addFile(file);
        }
        post.setCreatedAt(LocalDateTime.now());

        return post;
    }
}
