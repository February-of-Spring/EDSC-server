package com.february.edsc.domain.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class PostRequestDto {
	private final String email;
	private final String title;
	private final String content;
	private final String categoryName;
	private final List<String> images;
	private final List<String> files;

	public boolean isRequiredFieldNull() {
		return (email == null) || (email.isEmpty()) ||
			(title == null) || (title.isEmpty()) ||
			(content == null) || (content.isEmpty()) ||
			(categoryName == null) || (categoryName.isEmpty());
	}
}
