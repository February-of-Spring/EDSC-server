package com.february.edsc.controller;

import com.february.edsc.common.Error;
import com.february.edsc.common.ErrorMessage;
import com.february.edsc.domain.category.Category;
import com.february.edsc.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
@AllArgsConstructor
public class CategoryController {

	private final CategoryService categoryService;

	@GetMapping("/category")
	public ResponseEntity<Object> getCategories() {
		return ResponseEntity.ok().body(categoryService.getCategories());
	}

	@GetMapping(value = {"/category/{level1}", "/category/{level1}/{level2}"})
	public ResponseEntity<Object> getPostsByCategory(
		@PathVariable("level1") String level1,
		@PathVariable(name = "level2", required = false) Optional<String> level2) {
		Optional<Category> category1 = categoryService.findByName(level1);
		if (category1.isEmpty())
			return ResponseEntity.badRequest()
				.body(new Error(HttpStatus.BAD_REQUEST, ErrorMessage.NO_SUCH_CATEGORY));
		if (level2.isEmpty()) {
			return ResponseEntity.ok().body(categoryService.getPostsByParentCategory(category1.get()));
		}
		Optional<Category> category2 = categoryService.findByName(level2.get());
		if (category2.isEmpty())
			return ResponseEntity.badRequest()
				.body(new Error(HttpStatus.BAD_REQUEST, ErrorMessage.NO_SUCH_CATEGORY));
		return ResponseEntity.ok().body(categoryService.getPostsByChildCategory(category2.get()));
	}
}
