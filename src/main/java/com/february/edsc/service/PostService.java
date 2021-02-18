package com.february.edsc.service;

import com.february.edsc.domain.post.Post;
import com.february.edsc.domain.post.PostRequestDto;
import com.february.edsc.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class PostService {
	private final PostRepository postRepository;

	@Transactional
	public String createPost(PostRequestDto postRequestDto) {
		Long postId = postRepository.save(
			Post.builder()
				.user(postRequestDto.getUser())
				.title(postRequestDto.getTitle())
				.content(postRequestDto.getContent())
				.category(postRequestDto.getCategory())
				.images(postRequestDto.getImages())
				.files(postRequestDto.getFiles())
				.build()
		);
		return postId.toString();
	}
}
