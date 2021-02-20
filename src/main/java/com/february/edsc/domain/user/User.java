package com.february.edsc.domain.user;

import com.february.edsc.domain.user.like.Like;
import com.february.edsc.domain.post.Post;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();

	public UserResponseDto toUserResponseDto() {
	    return UserResponseDto.builder()
            .email(email)
            .name(name)
            .nickname(nickname)
            .profileImage(profileImage)
            .build();
	}
}
