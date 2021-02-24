package com.february.edsc.service;

import com.february.edsc.domain.category.Category;
import com.february.edsc.domain.post.*;
import com.february.edsc.domain.post.comment.Comment;
import com.february.edsc.domain.post.comment.CommentPackResponseDto;
import com.february.edsc.domain.post.file.File;
import com.february.edsc.domain.post.file.FileResponseDto;
import com.february.edsc.domain.post.image.Image;
import com.february.edsc.domain.post.image.ImageResponseDto;
import com.february.edsc.domain.user.User;
import com.february.edsc.domain.user.like.Like;
import com.february.edsc.domain.user.like.LikeResponseDto;
import com.february.edsc.repository.CategoryRepository;
import com.february.edsc.repository.CommentRepository;
import com.february.edsc.repository.LikeRepository;
import com.february.edsc.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostService {

	private final PostRepository postRepository;
	private final LikeRepository likeRepository;
	private final CommentRepository commentRepository;

	@Transactional
	public String createPost(PostRequestDto postRequestDto, User user, Category category) {
		Post post = postRepository.save(
			Post.builder()
				.user(user)
				.title(postRequestDto.getTitle())
				.content(postRequestDto.getContent())
				.category(category)
//				.images(postRequestDto.getImages())
//				.files(postRequestDto.getFiles())
				.build()
		);
		return post.getId().toString();
	}

	public Optional<Post> findById(Long id) {
		return postRepository.findById(id);
	}

	@Transactional
	public PostDetailResponseDto toDetailPostResponseDto(Post post) {
		post.upViewCount();
		List<CommentPackResponseDto> commentList = new ArrayList<>();
		List<Comment> comments = commentRepository.findAllByPostId(post.getId());
		List<Comment> parentComments = comments.stream()
			.filter((comment -> comment.getParent().getId().equals(comment.getId())))
			.collect(Collectors.toList());
		for (Comment parentComment : parentComments) {
			comments.remove(parentComment);
			List<Comment> childComments = comments.stream()
				.filter((comment -> comment.getParent().getId().equals(parentComment.getId())))
				.collect(Collectors.toList());
			commentList.add(
				CommentPackResponseDto.builder()
					.comment(parentComment.toCommentResponseDto())
					.childNum((long) childComments.size())
					.childList(childComments.stream()
						.map(Comment::toCommentResponseDto).collect(Collectors.toList()))
					.build()
			);
		}
		List<ImageResponseDto> imageList =
			post.getImages().stream().map(Image::toImageResponseDto).collect(Collectors.toList());
		List<FileResponseDto> fileList =
			post.getFiles().stream().map(File::toFileResponseDto).collect(Collectors.toList());
		return post.toPostDetailResponseDto(imageList, fileList, commentList);
	}


	@Transactional
	public void updatePost(Post post, PostRequestDto postRequestDto, Category category) {
		post.updatePost(postRequestDto, category);
	}

	public void deletePost(Post post) {
		postRepository.delete(post);
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

	public PostListResponseDto getMainPost() {
		List<PostResponseDto> postList = new ArrayList<>();
		for (long id = 4L; id <= 6L; id++) {
			Optional<Post> post = postRepository.findTopByCategoryIdOrderByCreatedAtDesc(id);
			post.ifPresent(value -> postList.add(value.toPostResponseDto()));
		}
		return PostListResponseDto.builder()
			.totalNum(postList.size())
			.postList(postList)
			.build();
	}
}
