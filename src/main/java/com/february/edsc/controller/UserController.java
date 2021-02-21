package com.february.edsc.controller;

import com.february.edsc.common.Error;
import com.february.edsc.common.ErrorMessage;
import com.february.edsc.domain.user.User;
import com.february.edsc.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
@AllArgsConstructor
public class UserController {

	private final UserService userService;

	@GetMapping("/users/{id}")
	public ResponseEntity<Object> getUser(@PathVariable Long id) {
		Optional<User> user = userService.findById(id);
		if (user.isEmpty()) {
			return ResponseEntity.badRequest()
				.body(new Error(HttpStatus.BAD_REQUEST, ErrorMessage.NO_SUCH_USER));
		} return ResponseEntity.ok().body(user.get().toUserDetailResponseDto());
	}

	@GetMapping("/users")
	public ResponseEntity<Object> getUsers() {
		return ResponseEntity.ok().body(userService.getUsers());
	}

	@GetMapping("/users/{id}/posts")
	public ResponseEntity<Object> getUserPosts(@PathVariable Long id) {
		Optional<User> user = userService.findById(id);
		if (user.isEmpty()) {
			return ResponseEntity.badRequest()
				.body(new Error(HttpStatus.BAD_REQUEST, ErrorMessage.NO_SUCH_USER));
		} return ResponseEntity.ok().body(userService.getUserPosts(id));
	}

	@GetMapping("/users/{id}/likes")
	public ResponseEntity<Object> getUserLikes(@PathVariable Long id) {
		Optional<User> user = userService.findById(id);
		if (user.isEmpty()) {
			return ResponseEntity.badRequest()
				.body(new Error(HttpStatus.BAD_REQUEST, ErrorMessage.NO_SUCH_USER));
		} return ResponseEntity.ok().body(userService.getUserLikes(id));
	}
}
