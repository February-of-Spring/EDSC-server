package com.february.edsc.domain.post.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CommentRequestDto {
	private final String email;
	private final String content;
	private final Boolean isPublic;
	private final Long parentId;

	public boolean isRequiredFieldNull() {
		return (email == null) || (email.isEmpty()) ||
		(content == null) || (content.isEmpty());
	}

	public boolean isUpdatingRequiredFieldNull() {
		return (email == null) || (email.isEmpty()) ||
			(content == null) || (content.isEmpty()) ||
			(isPublic == null) || (parentId == null);
	}
}
