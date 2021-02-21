package com.february.edsc.service;

import com.february.edsc.domain.post.Post;
import com.february.edsc.domain.post.PostListResponseDto;
import com.february.edsc.domain.post.PostResponseDto;
import com.february.edsc.domain.user.User;
import com.february.edsc.domain.user.like.Like;
import com.february.edsc.repository.LikeJpaRepository;
import com.february.edsc.repository.PostJpaRepository;
import com.february.edsc.repository.UserJpaRepository;
import com.february.edsc.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserJpaRepository userJpaRepository;
    private final PostJpaRepository postJpaRepository;
    private final LikeJpaRepository likeJpaRepository;

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
    public PostListResponseDto getUserPosts(Long userId) {
        List<PostResponseDto> posts =
            postJpaRepository.findAllByUserId(userId).stream()
                .map(Post::toPostResponseDto).collect(Collectors.toList());
        return PostListResponseDto.builder()
            .totalNum(posts.size())
            .postList(posts)
            .build();
    }

    @Transactional
    public List<PostResponseDto> getUserLikes(Long userId) {
        return likeJpaRepository.findAllByUserId(userId).stream()
            .map(Like::getPost).map(Post::toPostResponseDto)
            .collect(Collectors.toList());
    }
}
