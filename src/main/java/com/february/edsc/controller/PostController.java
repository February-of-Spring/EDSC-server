package com.february.edsc.controller;

import com.february.edsc.common.Error;
import com.february.edsc.common.ErrorMessage;
import com.february.edsc.domain.category.Category;
import com.february.edsc.domain.post.Post;
import com.february.edsc.domain.post.PostRequestDto;
import com.february.edsc.domain.user.User;
import com.february.edsc.service.CategoryService;
import com.february.edsc.service.PostService;
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
public class PostController {

	private final S3Service s3Service;
	private final UserService userService;
	private final PostService postService;
	private final CategoryService categoryService;

	@PostMapping("/posts")
	public ResponseEntity<Object> createPost(@RequestBody PostRequestDto postRequestDto) {
		if (postRequestDto.isRequiredFieldNull())
			return ResponseEntity.badRequest()
				.body(new Error(HttpStatus.BAD_REQUEST, ErrorMessage.REQUIRED_FIELD_NULL));
		Optional<User> user = userService.findByEmail(postRequestDto.getEmail());
		if (user.isEmpty()) {
			return ResponseEntity.badRequest()
				.body(new Error(HttpStatus.BAD_REQUEST, ErrorMessage.NO_SUCH_USER));
		}
		Optional<Category> category
			= categoryService.findByLevelAndName(2, postRequestDto.getCategoryName());
		if (category.isEmpty())
			return ResponseEntity.badRequest()
				.body(new Error(HttpStatus.BAD_REQUEST, ErrorMessage.NO_SUCH_CATEGORY));
		String postId = postService.createPost(postRequestDto, user.get(), category.get());
		return ResponseEntity.created(URI.create("/posts/" + postId)).build();
	}

	@GetMapping("/posts/main")
	public ResponseEntity<Object> getMainPost() {
		return ResponseEntity.ok().body(postService.getMainPost());
	}

	@GetMapping("/posts/{id}")
	public ResponseEntity<Object> getPost(@PathVariable Long id) {
		Optional<Post> post = postService.findById(id);
		if (post.isEmpty())
			return ResponseEntity.badRequest()
				.body(new Error(HttpStatus.BAD_REQUEST, ErrorMessage.NO_SUCH_POST));
		return ResponseEntity.ok().body(postService.toDetailPostResponseDto(post.get()));
	}

	@PutMapping("/posts/{id}")
	public ResponseEntity<Object> getPost(@PathVariable Long id,
		@RequestBody PostRequestDto postRequestDto) {
		if (postRequestDto.isRequiredFieldNull())
			return ResponseEntity.badRequest()
				.body(new Error(HttpStatus.BAD_REQUEST, ErrorMessage.REQUIRED_FIELD_NULL));
		Optional<Post> post = postService.findById(id);
		if (post.isEmpty())
			return ResponseEntity.badRequest()
				.body(new Error(HttpStatus.BAD_REQUEST, ErrorMessage.NO_SUCH_POST));
		Optional<Category> category
			= categoryService.findByLevelAndName(2, postRequestDto.getCategoryName());
		if (category.isEmpty())
			return ResponseEntity.badRequest()
				.body(new Error(HttpStatus.BAD_REQUEST, ErrorMessage.NO_SUCH_CATEGORY));
		if (!postRequestDto.getEmail().equals(post.get().getUser().getEmail()))
			return ResponseEntity.status(HttpStatus.FORBIDDEN)
				.body(new Error(HttpStatus.FORBIDDEN, ErrorMessage.UNAUTHORIZED_TO_UPDATE));
		postService.updatePost(post.get(), postRequestDto, category.get());
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/posts/{id}")
	public ResponseEntity<Object> deletePost(@PathVariable Long id) {
		Optional<Post> post = postService.findById(id);
		if (post.isEmpty())
			return ResponseEntity.badRequest()
				.body(new Error(HttpStatus.BAD_REQUEST, ErrorMessage.NO_SUCH_POST));
		postService.deletePost(post.get());
		return ResponseEntity.noContent().build();
	}

	// Todo: fix, "POST /posts/{id}/likes"
	@PostMapping("{userId}/posts/{id}/likes")
	public ResponseEntity<Object> likePost(@PathVariable Long userId, @PathVariable Long id) {
		Optional<Post> post = postService.findById(id);
		if (post.isEmpty())
			return ResponseEntity.badRequest()
				.body(new Error(HttpStatus.BAD_REQUEST, ErrorMessage.NO_SUCH_POST));

		// ToDo: fix, get Logged-in Member
		Optional<User> user = userService.findById(userId);
		if (user.isEmpty())
			return ResponseEntity.badRequest()
				.body(new Error(HttpStatus.BAD_REQUEST, ErrorMessage.NO_SUCH_USER));
		return ResponseEntity.ok().body(postService.likePost(post.get(), user.get()));
	}

	// Todo: fix, "DELETE /posts/{id}/likes"
	@DeleteMapping("{userId}/posts/{id}/likes")
	public ResponseEntity<Object> notLikePost(@PathVariable Long userId, @PathVariable Long id) {
		Optional<Post> post = postService.findById(id);
		if (post.isEmpty())
			return ResponseEntity.badRequest()
				.body(new Error(HttpStatus.BAD_REQUEST, ErrorMessage.NO_SUCH_POST));

		// ToDo: fix, get Logged-in Member
		Optional<User> user = userService.findById(userId);
		if (user.isEmpty())
			return ResponseEntity.badRequest()
				.body(new Error(HttpStatus.BAD_REQUEST, ErrorMessage.NO_SUCH_USER));
		return ResponseEntity.ok().body(postService.notLikePost(post.get(), user.get()));
	}

	@PostMapping("/posts/{id}/image")
	public ResponseEntity<Object> createPostImage(
		@RequestParam("image") MultipartFile multipartFile, @PathVariable Long id) throws IOException {
		Optional<Post> post = postService.findById(id);
		if (post.isEmpty()) {
			return ResponseEntity.badRequest()
				.body(new Error(HttpStatus.BAD_REQUEST, ErrorMessage.NO_SUCH_POST));
		}
		if (multipartFile.isEmpty()) {
			return ResponseEntity.badRequest()
				.body(new Error(HttpStatus.BAD_REQUEST, ErrorMessage.REQUIRED_FILE_NULL));
		}
		Optional<java.io.File> convertedFile = s3Service.convert(multipartFile);
		if (convertedFile.isEmpty()) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new Error(HttpStatus.INTERNAL_SERVER_ERROR, ErrorMessage.FAIL_FILE_CONVERT));
		}
		if (!s3Service.isValidExtension(convertedFile.get())) {
			return ResponseEntity.badRequest().body(
				new Error(HttpStatus.BAD_REQUEST, ErrorMessage.INVALID_FILE_TYPE));
		}
		String result = postService.createPostImage(convertedFile.get(), post.get());
		return ResponseEntity.created(URI.create(result)).build();
	}
}
