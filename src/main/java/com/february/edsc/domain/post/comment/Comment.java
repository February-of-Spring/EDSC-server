package com.february.edsc.domain.post.comment;

import com.february.edsc.domain.post.Post;
import com.february.edsc.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String content;

    @Column(name = "is_public")
    private boolean isPublic;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "modified_at")
    private Timestamp modifiedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "parent")
    private final List<Comment> child = new ArrayList<>();

    public void setParent(Comment parent) {
        this.parent = parent;
    }

    @Builder
    public Comment(String content, boolean isPublic, Post post, Comment parent, User user) {
        this.content = content;
        this.isPublic = isPublic;
        this.createdAt = new Timestamp(System.currentTimeMillis());
        this.modifiedAt = new Timestamp(System.currentTimeMillis());
        this.post = post;
        this.parent = parent;
        this.user = user;
    }

    public void updateComment(CommentRequestDto commentRequestDto) {
        content = commentRequestDto.getContent();
        isPublic = commentRequestDto.getIsPublic();
    }

    public CommentResponseDto toCommentResponseDto() {
        return CommentResponseDto.builder()
            .id(id)
            .content(content)
            .createdAt(createdAt)
            .modifiedAt(modifiedAt)
            .isPublic(isPublic)
            .user(user.toUserResponseDto())
            .build();
    }
}
