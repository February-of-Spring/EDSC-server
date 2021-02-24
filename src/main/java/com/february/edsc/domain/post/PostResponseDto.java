package com.february.edsc.domain.post;

import com.february.edsc.domain.category.CategoryResponseDto;
import com.february.edsc.domain.user.UserResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PostResponseDto {
	private final Long id;
	private final CategoryResponseDto category;
	private final UserResponseDto user;
	private final String title;
	private final String content;
	private final int likeCount;
	private final int viewCount;
}
