package com.february.edsc.domain.category;

import com.february.edsc.domain.post.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int level;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "category")
    private List<Post> posts = new ArrayList<>();

    @Builder
    public Category(String name, int level, Category parent) {
        this.name = name;
        this.level = level;
        this.parent = parent;
    }

    public CategoryResponseDto toCategoryParentResponseDto(Long parentNum) {
        return CategoryResponseDto.builder()
            .id(id)
            .name(name)
            .level(level)
            .postNum(parentNum)
            .parentCategoryId(parent.getId())
            .build();
    }

    public CategoryResponseDto toCategoryChildResponseDto() {
        return CategoryResponseDto.builder()
            .id(id)
            .name(name)
            .level(level)
            .postNum((long) posts.size())
            .parentCategoryId(parent.getId())
            .build();
    }
}
