package com.february.edsc.repository;

import com.february.edsc.domain.post.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
	Page<Post> findAllByUserId(Long userId, Pageable pageable);
	Optional<Post> findTopByCategoryIdOrderByCreatedAtDesc(long id);
}
