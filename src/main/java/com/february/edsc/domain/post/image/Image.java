package com.february.edsc.domain.post.image;

import com.february.edsc.domain.post.Post;
import com.february.edsc.domain.post.file.FileResponseDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Image {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String path;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    public ImageResponseDto toImageResponseDto() {
        return ImageResponseDto.builder()
            .id(id)
            .path(path)
            .build();
    }
}
