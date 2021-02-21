package com.february.edsc.repository;

import com.february.edsc.domain.category.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, Long> {
	Optional<Category> findByName(String category);
}
