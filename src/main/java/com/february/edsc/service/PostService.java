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
import com.february.edsc.repository.*;
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

	private final String IMAGE_PATH = "post-image";

	private final S3Service s3Service;
	private final PostRepository postRepository;
	private final LikeRepository likeRepository;
	private final ImageRepository imageRepository;
	private final CommentRepository commentRepository;

	@Transactional
	public String createPost(PostRequestDto postRequestDto, User user, Category category) {
		Post post = postRepository.save(
			Post.builder()
				.user(user)
				.title(postRequestDto.getTitle())
				.content(postRequestDto.getContent())
				.category(category)
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

	@Transactional
	public void deletePost(Post post) {
		post.getImages().forEach(this::deleteImage);
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

	@Transactional
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

	@Transactional
	public String createPostImage(java.io.File convertedFile, Post post) {
		Image image = imageRepository.save(Image.builder().post(post).build());
		String result = s3Service
			.upload(convertedFile, IMAGE_PATH, String.valueOf(image.getId()));
		image.updateImage(result);
		return result;
	}

	@Transactional
	public String updatePostImage(java.io.File convertedFile, Image image) {
		String result = s3Service
			.upload(convertedFile, IMAGE_PATH, image.getId().toString());
		image.updateImage(result);
		return result;
	}

	@Transactional
	public void deleteImage(Image image) {
		s3Service.delete(IMAGE_PATH, image.getId().toString());
		imageRepository.delete(image);
	}
}
