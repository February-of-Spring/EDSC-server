package com.february.edsc.service;

import com.february.edsc.domain.post.Post;
import com.february.edsc.domain.post.comment.Comment;
import com.february.edsc.domain.post.comment.CommentRequestDto;
import com.february.edsc.domain.user.User;
import com.february.edsc.repository.CommentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CommentService {

	private final CommentRepository commentRepository;

	@Transactional
	public String createComment(CommentRequestDto commentRequestDto, User user, Post post) {
		Comment comment = commentRepository.save(Comment.builder()
			.content(commentRequestDto.getContent())
			.user(user).post(post).build());
		comment.setParent(comment);
		return comment.getId().toString();
	}

	@Transactional
	public String createChildComment(CommentRequestDto commentRequestDto,
		User user, Post post, Comment parent) {
		return commentRepository.save(Comment.builder()
			.content(commentRequestDto.getContent())
			.parent(parent)
			.user(user).post(post).build())
			.getId().toString();
	}

	@Transactional
	public void updateComment(Comment comment, CommentRequestDto commentRequestDto) {
		comment.updateComment(commentRequestDto);
	}

	public Optional<Comment> findById(Long id) {
		return commentRepository.findById(id);
	}

	public void deleteComment(Comment comment) {
		commentRepository.delete(comment);
	}
}
