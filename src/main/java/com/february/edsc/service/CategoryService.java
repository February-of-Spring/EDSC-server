package com.february.edsc.service;

import com.february.edsc.domain.category.CategoryPackResponseDto;
import com.february.edsc.domain.category.Category;
import com.february.edsc.domain.category.CategoryResponseDto;
import com.february.edsc.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryService {

	private final CategoryRepository categoryRepository;

	public Optional<Category> findByName(String categoryName) {
		return categoryRepository.findByName(categoryName);
	}

	@Transactional
	public List<CategoryPackResponseDto> getCategories() {
		List<CategoryPackResponseDto> categoryPackResponseDtoList = new ArrayList<>();
		long id = 1;
		for (Category parentCategory: categoryRepository.findAllByLevel(1)) {
			List<CategoryResponseDto> child = getChild(parentCategory);
			long parentNum = child.stream().mapToLong(CategoryResponseDto::getPostNum).sum();
			categoryPackResponseDtoList.add(
				CategoryPackResponseDto.builder()
				.id(id++)
				.parent(parentCategory.toCategoryParentResponseDto(parentNum))
				.childNum((long) child.size())
				.child(child)
				.build());
		}
		return categoryPackResponseDtoList;
	}

	@Transactional
	public List<CategoryResponseDto> getChild(Category category) {
		return categoryRepository.findAllByParentIdAndLevel(category.getId(), 2)
			.stream().map(Category::toCategoryChildResponseDto).collect(Collectors.toList());
	}
}
