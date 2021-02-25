package com.february.edsc.domain.post.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class CommentPackResponseDto {
	private final CommentResponseDto comment;
	private final Long childNum;
	private final List<CommentResponseDto> childList;
}
