package com.february.edsc.domain.post;

import com.february.edsc.domain.user.UserResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class PostResponseDto {
	private final UserResponseDto user;
	private final String title;
	private final String content;
	private final int likeCount;
	private final int viewCount;
	private final Timestamp createdAt;
	private final Timestamp modifiedAt;
	private final String category;
	private final List<String> images;
	private final List<String> files;
}
