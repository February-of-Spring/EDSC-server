package com.february.edsc.service;

import com.february.edsc.domain.category.Category;
import com.february.edsc.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryService {

	private final CategoryRepository categoryRepository;

	public Optional<Category> findByName(String categoryName) {
		return categoryRepository.findByName(categoryName);
	}
}
