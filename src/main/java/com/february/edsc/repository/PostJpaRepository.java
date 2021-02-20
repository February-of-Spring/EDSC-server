package com.february.edsc.repository;

import com.february.edsc.domain.post.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostJpaRepository extends CrudRepository<Post, Long> {
}
