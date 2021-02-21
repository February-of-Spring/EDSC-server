package com.february.edsc.service;

import com.february.edsc.domain.category.Category;
import com.february.edsc.domain.post.Post;
import com.february.edsc.domain.post.PostRequestDto;
import com.february.edsc.domain.post.PostResponseDto;
import com.february.edsc.domain.user.User;
import com.february.edsc.domain.user.like.Like;
import com.february.edsc.domain.user.like.LikeResponseDto;
import com.february.edsc.repository.CategoryJpaRepository;
import com.february.edsc.repository.LikeRepository;
import com.february.edsc.repository.PostJpaRepository;
import com.february.edsc.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class PostService {

	private final PostRepository postRepository;
	private final PostJpaRepository postJpaRepository;
	private final LikeRepository likeRepository;
	private final CategoryJpaRepository categoryJpaRepository;

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

	public Optional<Post> findById(Long id) {
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

	@Transactional
	public LikeResponseDto likePost(Post post, User user) {
		if (likeRepository.findByPostIdAndUserId(post.getId(), user.getId()).isPresent())
			return post.toLikeResponseDto(post);
		likeRepository.save(Like.builder()
			.post(post)
			.user(user)
			.build()
		);
		post.upLikeCount();
		return post.toLikeResponseDto(post);
	}

	@Transactional
	public LikeResponseDto notLikePost(Post post, User user) {
		Optional<Like> like =
			likeRepository.findByPostIdAndUserId(post.getId(), user.getId());
		if (like.isEmpty())
			return post.toLikeResponseDto(post);
		likeRepository.delete(like.get());
		post.downLikeCount();
		return post.toLikeResponseDto(post);
	}
}
