package com.february.edsc.repository;

import com.february.edsc.domain.post.comment.Comment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, Long> {
	List<Comment> findAllByPostId(Long id);
}
