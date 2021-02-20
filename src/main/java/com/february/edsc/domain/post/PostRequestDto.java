package com.february.edsc.domain.post;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PostRequestDto {
	private final String email;
	private final String title;
	private final String content;
	private final String categoryName;
	private final List<String> images;
	private final List<String> files;
}
