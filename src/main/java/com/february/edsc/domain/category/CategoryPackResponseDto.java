package com.february.edsc.domain.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class CategoryPackResponseDto {
	private final Long id;
	private final CategoryResponseDto parent;
	private final Long childNum;
	private final List<CategoryResponseDto> child;
}
