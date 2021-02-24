package com.february.edsc.domain.post.file;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class FileResponseDto {
	private final Long id;
	private final String path;
}
