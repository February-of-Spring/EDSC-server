package com.february.edsc.repository;

import com.february.edsc.domain.user.like.Like;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
	Page<Like> findAllByUserId(Long userId, Pageable pageable);
	Optional<Like> findByPostIdAndUserId(Long postId, Long UserId);
}
