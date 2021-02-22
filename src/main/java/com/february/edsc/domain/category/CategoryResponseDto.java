package com.february.edsc.domain.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CategoryResponseDto {
	private final Long id;
	private final String name;
	private final int level;
	private final Long postNum;
	private final Long parentCategoryId;
}
