package com.february.edsc.domain.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CategoryRequestDto {
	private final String name;
	private final Integer level;
	private final Long parentId;

	public boolean isRequiredFieldNull() {
		if (level == null)
			return true;

		if (level == 1)
			return (name == null) || (name.isEmpty());
		else
			return (name == null) || (name.isEmpty()) || (parentId == null);
	}

	public boolean isUpdatingRequiredFieldNull() {
		return (name == null) || (name.isEmpty());
	}
}
