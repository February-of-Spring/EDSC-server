package com.february.edsc.service;

import com.february.edsc.domain.post.Post;
import com.february.edsc.domain.post.PostListResponseDto;
import com.february.edsc.domain.post.PostResponseDto;
import com.february.edsc.domain.user.User;
import com.february.edsc.domain.user.UserDetailResponseDto;
import com.february.edsc.domain.user.UserListResponseDto;
import com.february.edsc.domain.user.UserUpdateDto;
import com.february.edsc.domain.user.like.Like;
import com.february.edsc.repository.LikeRepository;
import com.february.edsc.repository.PostRepository;
import com.february.edsc.repository.UserJpaRepository;
import com.february.edsc.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final String PROFILE_IMAGE_PATH = "profile-image";

    private final S3Service s3Service;
    private final UserRepository userRepository;
    private final UserJpaRepository userJpaRepository;
    private final PostRepository postRepository;
    private final LikeRepository likeRepository;

    @Transactional
    public Long join(User user) {
        validateDuplicateMember(user);
        userRepository.save(user);

        return user.getId();
    }

    private void validateDuplicateMember(User user) {
        List<User> foundUsers = userRepository.findByName(user.getName());
        if (!foundUsers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    public Optional<User> findByEmail(String email) {
        return userJpaRepository.findByEmail(email);
    }
    
    public Optional<User> findById(Long userId) {
        return userJpaRepository.findById(userId);
    }

    @Transactional
    public PostListResponseDto getUserPosts(Long userId, Pageable pageable) {
        List<PostResponseDto> posts =
            postRepository.findAllByUserId(userId, pageable).stream()
                .map(Post::toPostResponseDto).collect(Collectors.toList());
        return PostListResponseDto.builder()
            .totalNum(posts.size())
            .postList(posts)
            .build();
    }

    @Transactional
    public PostListResponseDto getUserLikes(Long userId, Pageable pageable) {
        List<PostResponseDto> posts =
            likeRepository.findAllByUserId(userId, pageable).stream()
            .map(Like::getPost).map(Post::toPostResponseDto)
            .collect(Collectors.toList());
        return PostListResponseDto.builder()
            .totalNum(posts.size())
            .postList(posts)
            .build();
    }

    public Object getUsers() {
        List<UserDetailResponseDto> users = new ArrayList<>();
        userJpaRepository.findAll()
            .forEach(user -> users.add(user.toUserDetailResponseDto()));
        return UserListResponseDto.builder()
            .totalNum(users.size())
            .userList(users)
            .build();
    }

    @Transactional
    public void updateUser(User user, UserUpdateDto userUpdateDto) {
        user.updateUser(userUpdateDto);
    }

    @Transactional
    public String updateUserImage(File convertedFile, User user) {
        String result = s3Service.upload(convertedFile, PROFILE_IMAGE_PATH, user.getId().toString());
        user.updateImage(result);
        return result;
    }

    @Transactional
    public void deleteUserImage(User user) {
        s3Service.delete(PROFILE_IMAGE_PATH, user.getId().toString());
        user.updateImage("");
    }
}
