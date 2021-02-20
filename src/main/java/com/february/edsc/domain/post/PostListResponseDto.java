package com.february.edsc.domain.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class PostListResponseDto {
	private final int totalNum;
	private final List<PostResponseDto> postList;
}
