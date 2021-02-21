package com.february.edsc.repository;

import com.february.edsc.domain.user.like.Like;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface LikeRepository extends CrudRepository<Like, Long> {
	List<Like> findAllByUserId(Long userId);
	Optional<Like> findByPostIdAndUserId(Long postId, Long UserId);
}
