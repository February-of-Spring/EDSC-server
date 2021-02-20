package com.february.edsc.controller;

import com.february.edsc.common.Error;
import com.february.edsc.common.ErrorMessage;
import com.february.edsc.domain.post.PostRequestDto;
import com.february.edsc.domain.user.User;
import com.february.edsc.service.PostService;
import com.february.edsc.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
}
