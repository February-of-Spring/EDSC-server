package com.february.edsc.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Category {

    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;
    private String description;

    @OneToMany(mappedBy = "category")
    private List<Post> posts = new ArrayList<>();
}
