package com.february.edsc.controller;

import com.february.edsc.common.Error;
import com.february.edsc.common.ErrorMessage;
import com.february.edsc.domain.post.Post;
import com.february.edsc.domain.post.PostRequestDto;
import com.february.edsc.domain.user.User;
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
public class PostController {
	private final UserService userService;
	private final PostService postService;

	@PostMapping("/posts")
	public ResponseEntity<Object> createPost(@RequestBody PostRequestDto postRequestDto) {
		Optional<User> user = userService.findByEmail(postRequestDto.getEmail());
		if (user.isEmpty()) {
			return ResponseEntity.badRequest()
				.body(new Error(HttpStatus.BAD_REQUEST, ErrorMessage.NO_SUCH_USER));
		}
		String postId = postService.createPost(postRequestDto, user.get());
		URI location = URI.create("/posts/" + postId);
		return ResponseEntity.created(location).build();
	}

	@GetMapping("/posts/{id}")
	public ResponseEntity<Object> getPost(@PathVariable Long id) {
		Optional<Post> post = postService.getPost(id);
		if (post.isEmpty())
			return ResponseEntity.badRequest()
				.body(new Error(HttpStatus.BAD_REQUEST, ErrorMessage.NO_SUCH_POST));
		return ResponseEntity.ok().body(postService.toPostResponseDto(post.get()));
	}

	@PutMapping("/posts/{id}")
	public ResponseEntity<Object> getPost(@PathVariable Long id,
		@RequestBody PostRequestDto postRequestDto) {
		Optional<Post> post = postService.getPost(id);
		if (post.isEmpty())
			return ResponseEntity.badRequest()
				.body(new Error(HttpStatus.BAD_REQUEST, ErrorMessage.NO_SUCH_POST));
		postService.updatePost(post.get(), postRequestDto);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/posts/{id}")
	public ResponseEntity<Object> deletePost(@PathVariable Long id) {
		Optional<Post> post = postService.getPost(id);
		if (post.isEmpty())
			return ResponseEntity.badRequest()
				.body(new Error(HttpStatus.BAD_REQUEST, ErrorMessage.NO_SUCH_POST));
		postService.deletePost(post.get());
		return ResponseEntity.noContent().build();
	}
}
