package com.february.edsc.controller;

import com.february.edsc.domain.post.PostRequestDto;
import com.february.edsc.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.URI;

@Controller
@AllArgsConstructor
public class PostController {
	private final PostService postService;

	@PostMapping("/posts")
	public ResponseEntity createPost(@RequestBody PostRequestDto postRequestDto) {
		String postId = postService.createPost(postRequestDto);
		URI location = URI.create("/posts/" + postId);
		return ResponseEntity.created(location).build();
	}
}
