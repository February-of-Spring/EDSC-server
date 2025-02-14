package com.february.edsc.domain.post;

import com.february.edsc.domain.category.Category;
import com.february.edsc.domain.post.comment.Comment;
import com.february.edsc.domain.post.comment.CommentPackResponseDto;
import com.february.edsc.domain.post.file.File;
import com.february.edsc.domain.post.file.FileResponseDto;
import com.february.edsc.domain.post.image.Image;
import com.february.edsc.domain.post.image.ImageResponseDto;
import com.february.edsc.domain.user.User;
import com.february.edsc.domain.user.like.LikeResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    private Timestamp createdAt;

    private Timestamp modifiedAt;

    private int likeCount;

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

    public void setUser(User user) {
        this.user = user;
        user.getPosts().add(this);
    }

    public void setCategory(Category category) {
        this.category = category;
        category.getPosts().add(this);
    }

    @Builder
    public Post(String title, String content, User user,
                Category category, List<Image> images, List<File> files) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.createdAt = new Timestamp(System.currentTimeMillis() + 32400000L);
        this.modifiedAt = new Timestamp(System.currentTimeMillis() + 32400000L);
        this.category = category;
        this.images = images;
        this.files = files;
    }

    public PostDetailResponseDto toPostDetailResponseDto(
        List<ImageResponseDto> imageList, List<FileResponseDto> fileList,
        List<CommentPackResponseDto> commentList) {
        return PostDetailResponseDto.builder()
            .id(id)
            .user(user.toUserResponseDto())
            .title(title)
            .content(content)
            .likeCount(likeCount)
            .viewCount(viewCount)
            .createdAt(createdAt)
            .modifiedAt(modifiedAt)
            .category(category.toCategoryChildResponseDto())
            .imageList(imageList)
            .fileList(fileList)
            .commentNum(comments.size())
            .commentList(commentList)
            .build();
    }

    public PostResponseDto toPostResponseDto() {
        return PostResponseDto.builder()
            .id(id)
            .category(category.toCategoryChildResponseDto())
            .user(user.toUserResponseDto())
            .title(title)
            .content(content)
            .likeCount(likeCount)
            .viewCount(viewCount)
            .build();
    }

    public void upViewCount() {
        viewCount++;
    }

    public void updatePost(PostRequestDto postRequestDto, Category category) {
        this.title = postRequestDto.getTitle();
        this.content = postRequestDto.getContent();
        this.modifiedAt = new Timestamp(System.currentTimeMillis() + 32400000L);
        this.category = category;
    }

    public void upLikeCount() {
        likeCount++;
    }

    public void downLikeCount() {
        likeCount--;
    }

    public LikeResponseDto toLikeResponseDto(Post post) {
        return LikeResponseDto.builder()
            .likeCount(post.likeCount)
            .build();
    }
}
