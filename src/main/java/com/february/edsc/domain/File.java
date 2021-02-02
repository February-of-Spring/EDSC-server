package com.february.edsc.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class File {

    @Id
    @GeneratedValue
    @Column(name = "file_id")
    private Long id;

    private String path;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

}
