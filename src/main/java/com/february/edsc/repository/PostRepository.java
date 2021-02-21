package com.february.edsc.repository;

import com.february.edsc.domain.post.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostRepository extends CrudRepository<Post, Long> {
	List<Post> findAllByUserId(Long userId);
}
