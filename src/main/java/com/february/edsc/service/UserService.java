package com.february.edsc.service;

import com.february.edsc.domain.user.User;
import com.february.edsc.repository.UserJpaRepository;
import com.february.edsc.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserJpaRepository userJpaRepository;

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

    // 회원 전체 조회
    public List<User> findMembers() {
        return userRepository.findAll();
    }

    // 단건 조회
    public User findOne(Long memberId) {
        return userRepository.findOne(memberId);
    }

    public Optional<User> findByEmail(String email) {
        return userJpaRepository.findByEmail(email);
    }
}
