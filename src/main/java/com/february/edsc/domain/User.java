package com.february.edsc.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter @Setter
public class User {

    @Id
    @GeneratedValue
    @Column("user_id")
    private Long id;

    private String email;
    private String password;
    private String name;
    private String nickname;

    @Column(name = "profile_image")
    private String profileImage;
    private String phone;
}
