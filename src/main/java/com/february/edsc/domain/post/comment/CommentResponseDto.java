package com.february.edsc.domain.post.comment;

import com.february.edsc.domain.user.UserResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@Builder
@AllArgsConstructor
public class CommentResponseDto {
	private final Long id;
	private final UserResponseDto user;
	private final String content;
	private final Boolean isPublic;
	private final Timestamp createdAt;
	private final Timestamp modifiedAt;
}
