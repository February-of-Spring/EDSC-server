package com.february.edsc.domain.post.image;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ImageResponseDto {
	private final Long id;
	private final String path;
}
