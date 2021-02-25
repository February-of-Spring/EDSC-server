package com.february.edsc.domain.post.file;

import com.february.edsc.domain.post.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class File {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String path;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    public FileResponseDto toFileResponseDto() {
        return FileResponseDto.builder()
            .id(id)
            .path(path)
            .build();
    }
}
