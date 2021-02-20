package com.february.edsc.service;

import com.february.edsc.domain.category.Category;
import com.february.edsc.domain.post.Post;
import com.february.edsc.domain.post.PostRequestDto;
import com.february.edsc.domain.post.PostResponseDto;
import com.february.edsc.domain.user.User;
import com.february.edsc.repository.CategoryJpaRepository;
import com.february.edsc.repository.PostJpaRepository;
import com.february.edsc.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class PostService {
	private final CategoryJpaRepository categoryJpaRepository;
	private final PostRepository postRepository;
	private final PostJpaRepository postJpaRepository;

	@Transactional
	public String createPost(PostRequestDto postRequestDto, User user) {
		Category category = getCategory(postRequestDto.getCategoryName());
		Long postId = postRepository.save(
			Post.builder()
				.user(user)
				.title(postRequestDto.getTitle())
				.content(postRequestDto.getContent())
				.category(category)
//				.images(postRequestDto.getImages())
//				.files(postRequestDto.getFiles())
				.build()
		);
		return postId.toString();
	}

	@Transactional
	public Category getCategory(String categoryName) {
		Optional<Category> category = categoryJpaRepository.findByName(categoryName);
		if (category.isEmpty())
			return categoryJpaRepository.save(Category.builder().name(categoryName).build());
		return category.get();
	}

	public Optional<Post> getPost(Long id) {
		return postJpaRepository.findById(id);
	}

	@Transactional
	public PostResponseDto toPostResponseDto(Post post) {
		post.upViewCount();
		return post.toPostResponseDto();
	}

	@Transactional
	public void updatePost(Post post, PostRequestDto postRequestDto) {
		Category category = getCategory(postRequestDto.getCategoryName());
		post.updatePost(postRequestDto, category);
	}
	
	public void deletePost(Post post) {
		postJpaRepository.delete(post);
	}
}
