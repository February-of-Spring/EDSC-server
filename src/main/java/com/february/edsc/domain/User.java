package com.february.edsc.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class User {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String email;
    private String password;
    private String name;
    private String nickname;

    @Column(name = "profile_image")
    private String profileImage;
    private String phone;

    @OneToMany(mappedBy = "user")
    private List<Like> likes = new ArrayList<>();

}
