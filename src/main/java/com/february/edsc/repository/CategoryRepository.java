package com.february.edsc.repository;

import com.february.edsc.domain.category.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, Long> {
	Optional<Category> findByLevelAndName(int level, String name);
	List<Category> findAllByLevel(int level);
	List<Category> findAllByParentIdAndLevel(Long parentId, int level);
	List<Category> findAllByParentId(Long id);
}
