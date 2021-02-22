package com.february.edsc.controller;

import com.february.edsc.common.Error;
import com.february.edsc.common.ErrorMessage;
import com.february.edsc.domain.category.Category;
import com.february.edsc.domain.category.CategoryRequestDto;
import com.february.edsc.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
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

	@PostMapping("/category")
	public ResponseEntity<Object> createCategory(@RequestBody CategoryRequestDto categoryRequestDto) {
		if (categoryRequestDto.isRequiredFieldNull())
			return ResponseEntity.badRequest()
				.body(new Error(HttpStatus.BAD_REQUEST, ErrorMessage.REQUIRED_FIELD_NULL));
		Optional<Category> category
			= categoryService.findByName(categoryRequestDto.getName());

		String result;
		if (categoryRequestDto.getLevel() == 2) {
			if (category.isPresent() &&
				category.get().getParent().getId().equals(categoryRequestDto.getParentId()))
				return ResponseEntity.badRequest()
					.body(new Error(HttpStatus.BAD_REQUEST, ErrorMessage.DUPLICATE_CATEGORY));
			Optional<Category> parentCategory
				= categoryService.findById(categoryRequestDto.getParentId());
			if (parentCategory.isEmpty())
				return ResponseEntity.badRequest()
					.body(new Error(HttpStatus.BAD_REQUEST, ErrorMessage.NO_SUCH_PARENT_CATEGORY));
			result = categoryService.createCategoryChild(categoryRequestDto, parentCategory.get());
		} else {
			if (category.isPresent() && category.get().getLevel() == 1)
				return ResponseEntity.badRequest()
					.body(new Error(HttpStatus.BAD_REQUEST, ErrorMessage.DUPLICATE_CATEGORY));
			result = categoryService.createCategoryParent(categoryRequestDto);
		}
		result = URLEncoder.encode("/category/" + result, StandardCharsets.UTF_8);
		return ResponseEntity.created(URI.create(result)).build();
	}

	@DeleteMapping(value = {"/category/{level1}", "/category/{level1}/{level2}"})
	public ResponseEntity<Object> deleteCategory(
		@PathVariable("level1") String level1,
		@PathVariable(name = "level2", required = false) Optional<String> level2) {
		Optional<Category> category1 = categoryService.findByName(level1);
		if (category1.isEmpty())
			return ResponseEntity.badRequest()
				.body(new Error(HttpStatus.BAD_REQUEST, ErrorMessage.NO_SUCH_CATEGORY));
		if (level2.isEmpty()) {
			List<Category> childCategories = categoryService.findAllByParentId(category1.get());
			if (childCategories.size() != 1)
				return ResponseEntity.badRequest()
					.body(new Error(HttpStatus.BAD_REQUEST, ErrorMessage.CATEGORY_HAS_CHILD));
			categoryService.deleteCategory(category1.get());
		} else {
			Optional<Category> category2 = categoryService.findByName(level2.get());
			if (category2.isEmpty())
				return ResponseEntity.badRequest()
					.body(new Error(HttpStatus.BAD_REQUEST, ErrorMessage.NO_SUCH_CATEGORY));
			if (category2.get().getPosts().size() != 0)
				return ResponseEntity.badRequest()
					.body(new Error(HttpStatus.BAD_REQUEST, ErrorMessage.CATEGORY_HAS_POSTS));
			categoryService.deleteCategory(category2.get());
		}
		return ResponseEntity.noContent().build();
	}
}
