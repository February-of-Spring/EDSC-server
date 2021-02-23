package com.february.edsc.controller;

import com.february.edsc.common.Error;
import com.february.edsc.common.ErrorMessage;
import com.february.edsc.domain.user.User;
import com.february.edsc.domain.user.UserUpdateDto;
import com.february.edsc.service.S3Service;
import com.february.edsc.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class UserController {

	private  final S3Service s3Service;
	private final UserService userService;

	@GetMapping("/users/{id}")
	public ResponseEntity<Object> getUser(@PathVariable Long id) {
		Optional<User> user = userService.findById(id);
		if (user.isEmpty()) {
			return ResponseEntity.badRequest()
				.body(new Error(HttpStatus.BAD_REQUEST, ErrorMessage.NO_SUCH_USER));
		} return ResponseEntity.ok().body(user.get().toUserDetailResponseDto());
	}

	@PutMapping("/users/{id}")
	public ResponseEntity<Object> updateUser(@PathVariable Long id,
		@RequestBody UserUpdateDto userUpdateDto) {
		if (userUpdateDto.isRequiredFieldNull())
			return ResponseEntity.badRequest()
				.body(new Error(HttpStatus.BAD_REQUEST, ErrorMessage.REQUIRED_FIELD_NULL));
		Optional<User> user = userService.findById(id);
		if (user.isEmpty())
			return ResponseEntity.badRequest()
				.body(new Error(HttpStatus.BAD_REQUEST, ErrorMessage.NO_SUCH_USER));
		if (!user.get().getEmail().equals(userUpdateDto.getEmail()))
			return ResponseEntity.status(HttpStatus.FORBIDDEN)
				.body(new Error(HttpStatus.FORBIDDEN, ErrorMessage.UNAUTHORIZED_TO_UPDATE));
		userService.updateUser(user.get(), userUpdateDto);
		return ResponseEntity.ok().build();
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

	@PatchMapping("/users/{id}/image")
	public ResponseEntity<Object> updateUserImage(
		@RequestParam("image") MultipartFile multipartFile, @PathVariable Long id) throws IOException {
		Optional<User> user = userService.findById(id);
		if (user.isEmpty()) {
			return ResponseEntity.badRequest().body(
				new Error(HttpStatus.BAD_REQUEST, ErrorMessage.NO_SUCH_USER));
		}
		if (multipartFile.isEmpty()) {
			return ResponseEntity.badRequest().body(
				new Error(HttpStatus.BAD_REQUEST, ErrorMessage.REQUIRED_FIELD_NULL));
		}
		Optional<File> convertedFile = s3Service.convert(multipartFile);
		if (convertedFile.isEmpty()) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new Error(HttpStatus.INTERNAL_SERVER_ERROR,
					ErrorMessage.FAIL_FILE_CONVERT));
		}
		if (!s3Service.isValidExtension(convertedFile.get())) {
			return ResponseEntity.badRequest().body(
				new Error(HttpStatus.BAD_REQUEST, ErrorMessage.INVALID_FILE_TYPE));
		}
		String result = userService.updateUserImage(convertedFile.get(), user.get());
		return ResponseEntity.created(URI.create(result)).build();
	}

	@DeleteMapping("/users/{id}/image")
	public ResponseEntity<Object> deleteUserImage(@PathVariable Long id) {
		Optional<User> user = userService.findById(id);
		if (user.isEmpty()) {
			return ResponseEntity.badRequest()
				.body(new Error(HttpStatus.BAD_REQUEST, ErrorMessage.NO_SUCH_USER));
		}
		userService.deleteUserImage(user.get());
		return ResponseEntity.noContent().build();
	}
}
