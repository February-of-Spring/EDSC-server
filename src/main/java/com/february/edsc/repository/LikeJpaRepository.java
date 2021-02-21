package com.february.edsc.repository;

import com.february.edsc.domain.user.like.Like;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface LikeJpaRepository extends CrudRepository<Like, Long> {
	Optional<Like> findByPostIdAndUserId(Long postId, Long UserId);
}
