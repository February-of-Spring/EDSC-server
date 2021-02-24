package com.february.edsc.repository;

import com.february.edsc.domain.post.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends CrudRepository<Post, Long> {
	List<Post> findAllByUserId(Long userId);
	Optional<Post> findTopByCategoryIdOrderByCreatedAtDesc(long id);
}
