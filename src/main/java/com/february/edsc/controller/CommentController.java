package com.february.edsc.controller;

import com.february.edsc.common.Error;
import com.february.edsc.common.ErrorMessage;
import com.february.edsc.domain.post.Post;
import com.february.edsc.domain.post.comment.Comment;
import com.february.edsc.domain.post.comment.CommentRequestDto;
import com.february.edsc.domain.user.User;
import com.february.edsc.service.CommentService;
import com.february.edsc.service.PostService;
import com.february.edsc.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class CommentController {

	private final UserService userService;
	private final PostService postService;
	private final CommentService commentService;

	@PostMapping("/posts/{postId}/comments")
	public ResponseEntity<Object> createComment(
		@PathVariable Long postId,
		@RequestBody CommentRequestDto commentRequestDto) {
		if (commentRequestDto.isRequiredFieldNull())
			return ResponseEntity.badRequest()
			.body(new Error(HttpStatus.BAD_REQUEST, ErrorMessage.REQUIRED_FIELD_NULL));
		Optional<User> user = userService.findByEmail(commentRequestDto.getEmail());
		if (user.isEmpty()) {
			return ResponseEntity.badRequest()
				.body(new Error(HttpStatus.BAD_REQUEST, ErrorMessage.NO_SUCH_USER));
		}
		Optional<Post> post = postService.findById(postId);
		if (post.isEmpty()) {
			return ResponseEntity.badRequest()
				.body(new Error(HttpStatus.BAD_REQUEST, ErrorMessage.NO_SUCH_POST));
		}
		String commentId;
		if (commentRequestDto.getParentId() != null) {
			Optional<Comment> parentComment =
				commentService.findById(commentRequestDto.getParentId());
			if (parentComment.isEmpty())
				return ResponseEntity.badRequest()
				.body(new Error(HttpStatus.BAD_REQUEST, ErrorMessage.NO_SUCH_PARENT_COMMENT));
			commentId =
				commentService.createChildComment(commentRequestDto,
					user.get(), post.get(), parentComment.get());
		} else
			commentId = commentService.createComment(commentRequestDto, user.get(), post.get());
		return ResponseEntity.created(
			URI.create("/posts/" + postId + "/comments/" + commentId)).build();
	}

	@PutMapping("/posts/{postId}/comments/{commentId}")
	public ResponseEntity<Object> updateComment(
		@PathVariable Long postId,
		@PathVariable Long commentId,
		@RequestBody CommentRequestDto commentRequestDto) {
		if (commentRequestDto.isUpdatingRequiredFieldNull())
			return ResponseEntity.badRequest()
				.body(new Error(HttpStatus.BAD_REQUEST, ErrorMessage.REQUIRED_FIELD_NULL));
		Optional<Comment> comment = commentService.findById(commentId);
		if (comment.isEmpty())
			return ResponseEntity.badRequest()
				.body(new Error(HttpStatus.BAD_REQUEST, ErrorMessage.NO_SUCH_COMMENT));
		Optional<User> user = userService.findByEmail(commentRequestDto.getEmail());
		if (user.isEmpty() || !user.get().getEmail().equals(commentRequestDto.getEmail()))
			return ResponseEntity.badRequest()
				.body(new Error(HttpStatus.UNAUTHORIZED, ErrorMessage.UNAUTHORIZED_TO_UPDATE));
		if (!comment.get().getPost().getId().equals(postId))
			return ResponseEntity.badRequest()
				.body(new Error(HttpStatus.BAD_REQUEST, ErrorMessage.BAD_REQUEST));
		commentService.updateComment(comment.get(), commentRequestDto);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/posts/{postId}/comments/{commentId}")
	public ResponseEntity<Object> deleteComment(
		@PathVariable Long postId, @PathVariable Long commentId) {
		Optional<Comment> comment = commentService.findById(commentId);
		if (comment.isEmpty())
			return ResponseEntity.badRequest()
				.body(new Error(HttpStatus.BAD_REQUEST, ErrorMessage.NO_SUCH_COMMENT));
		// ToDo: check FORBIDDEN, return ErrorMessage.UNAUTHORIZED_TO_DELETE
		if (!comment.get().getPost().getId().equals(postId))
			return ResponseEntity.badRequest()
				.body(new Error(HttpStatus.BAD_REQUEST, ErrorMessage.BAD_REQUEST));
		commentService.deleteComment(comment.get());
		return ResponseEntity.noContent().build();
	}
}
