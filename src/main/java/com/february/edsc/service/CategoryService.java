package com.february.edsc.service;

import com.february.edsc.domain.category.Category;
import com.february.edsc.repository.CategoryJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryService {

	private final CategoryJpaRepository categoryJpaRepository;

	public Optional<Category> findByName(String categoryName) {
		return categoryJpaRepository.findByName(categoryName);
	}
}
