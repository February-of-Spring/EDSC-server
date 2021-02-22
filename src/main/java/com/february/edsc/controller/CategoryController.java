package com.february.edsc.controller;

import com.february.edsc.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class CategoryController {

	private final CategoryService categoryService;

	@GetMapping("/categories")
	public ResponseEntity<Object> getCategories() {
		return ResponseEntity.ok().body(categoryService.getCategories());
	}
}
